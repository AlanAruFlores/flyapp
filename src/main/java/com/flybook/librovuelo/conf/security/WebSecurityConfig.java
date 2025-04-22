package com.flybook.librovuelo.conf.security;

import com.flybook.librovuelo.conf.auth.UserDetailsServiceImpl;
import com.flybook.librovuelo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.flybook.librovuelo.conf.security.WebUserRole.ADMINISTRADOR;
import static com.flybook.librovuelo.conf.security.WebUserRole.LIDER;
import static com.flybook.librovuelo.conf.security.WebUserRole.OPERACIONES;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                .authorizeHttpRequests((requests) -> {
                    try {



                        requests.
                                requestMatchers("/css/**", "/js/**", "/img/**", "/registration").permitAll()
                                .requestMatchers("/forgot_password", "/reset_password", "/csrf").permitAll()
                                .requestMatchers("/tripulante/perfil/admin/**").hasAnyRole(ADMINISTRADOR.name(), LIDER.name())
                                .requestMatchers("/lider/**").hasAnyRole(ADMINISTRADOR.name(), LIDER.name())
                                .requestMatchers("/operaciones/**").hasAnyRole(ADMINISTRADOR.name(), OPERACIONES.name())
                                .anyRequest().authenticated()
                                .and()
                                .exceptionHandling()
                                .accessDeniedHandler(accessDeniedHandler())
                                .and()
                                .formLogin()
                                .loginPage("/login")
                                .permitAll()
                                .and()
//                                .rememberMe()
//                                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                                .and()
                                .logout()
                                .permitAll()
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID", "remember-me")
                                .logoutSuccessUrl("/login");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {

        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .parentAuthenticationManager(null)
                .userDetailsService(new UserDetailsServiceImpl(userRepository))
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }
}

//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) : permite que javascript lea el token.
// Spring Security agrega el token en la cookie (XSRF-TOKEN) y el cliente debe enviar el mismo token
// con cada solicitud en el encabezado (X-XSRF-TOKEN) .
// El servidor validará el token desde el encabezado de la solicitud y el token generado.

// hasRole, hasAnyRole: Estas expresiones son las encargadas de definir el control de acceso o autorización
// a URLs y métodos específicos en nuestra aplicación:

// hasAuthority, hasAnyAuthority: Los roles y las autoridades son similares en Spring.
// La principal diferencia es que los roles tienen una semántica especial. A partir de Spring Security 4,
// el prefijo ' ROLE_ ' se agrega automáticamente (si aún no está allí) mediante cualquier método relacionado
// con el rol.

// Entonces hasAuthority('ROLE_ADMIN') es similar a hasRole('ADMIN') porque el prefijo ' ROLE_ ' se agrega automáticamente.
// authorizeRequests()Permite restringir el acceso en función de las implementaciones
// de HttpServletRequestuso RequestMatcher.
// permitAll()Esto permitirá el acceso público, es decir, cualquiera puede acceder al punto final PUBLIC_URL
// sin autenticación.
// anyRequest().authenticated() restringirá el acceso para cualquier otro punto final que no sea PUBLIC_URL ,
// y el usuario debe estar autenticado.

// antMatchers: Es antMatchers()un método HTTP de Springboot que se utiliza para configurar las rutas de URL
// desde las que la seguridad de la aplicación Springboot debe permitir solicitudes en función de las funciones
// del usuario. El antmatchers()método es un método sobrecargado que recibe tanto los métodos de solicitud HTTP
// como las URL específicas como sus argumentos.