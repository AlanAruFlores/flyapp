package com.flybook.librovuelo.web;

import com.flybook.librovuelo.conf.auth.UserValidator;
import com.flybook.librovuelo.exceptions.InvalidCredentialsUserException;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.AdjuntoDocumentacionRepository;
import com.flybook.librovuelo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private GeneracionService generacionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private LocalidadService localidadService;
    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ImageProfileService imageProfileService;

    @Autowired
    private DocumentacionService documentacionService;
    @Autowired
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

    @GetMapping("/registration")
    public String registration(Model model) {
        List<TipoCargo> cargos = List.of(TipoCargo.values());
        List<Generacion> generaciones = this.generacionService.findAll();

        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());
        model.addAttribute("cargos", cargos);
        model.addAttribute("generaciones", generaciones);
        model.addAttribute("title", "Registro de usuario");

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            List<TipoCargo> cargos = List.of(TipoCargo.values());
            List<Generacion> generaciones = this.generacionService.findAll();

            model.addAttribute("userForm", userForm);
            model.addAttribute("cargos", cargos);
            model.addAttribute("generaciones", generaciones);
            model.addAttribute("title", "Registro de usuario");
            return "registration";
        } else {
            userService.save(userForm);
            securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
            redirectAttributes.addFlashAttribute("confirmado", "El registro fue exitoso");
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        ;
        return "login";
    }

    @GetMapping("/login-access-request")
    public String confirmarAcceso(Model model){
        if (!securityService.isAuthenticated())
            return "redirect:/login";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        User userToAuthenticateForm  = new User();
        userToAuthenticateForm.setUsername(user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("user_authentication",userToAuthenticateForm);
        return "login-access-request";
    }

    @PostMapping("/login-access-request")
    public String formularioConfirmarAcesso(@ModelAttribute("user_authentication") User userToAuthenticate, Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());/*Password encriptada*/

        try{
            this.userService.verifyIfUserHasCorrectCredentials(user, userToAuthenticate);
            request.getSession().setAttribute("accessVerified", true);

        }catch(InvalidCredentialsUserException ex){
            User userToAuthenticateForm  = new User();
            userToAuthenticateForm.setUsername(user.getUsername());
            model.addAttribute("error", "Your password is Invalid. Try Again");
            model.addAttribute("user", user);
            model.addAttribute("user_authentication",userToAuthenticateForm);
            return "login-access-request";
        }

        return "redirect:"+request.getSession().getAttribute("pendingRequest");
    }

    @GetMapping({"/", "/home"})
    public String home(@RequestParam Map<String, Object> params, Model model) {
        //Paginacion

        int recordsQuantity = Integer.MAX_VALUE;

        int page = params.get("page") != null ? (Integer.parseInt(params.get("page").toString()) - 1) : 0;
        String sortField = params.get("sortField") != null ? params.get("sortField").toString() : "fecha";
        String orientation = params.get("sortOrientation") != null ? params.get("sortOrientation").toString() : "Desc";

        Sort sort;
        sort = orientation.equals("Desc") ? Sort.by(sortField).descending() : Sort.by(sortField).ascending();

        PageRequest pageRequest = PageRequest.of(page, recordsQuantity, sort);

        //Fin Paginacion

        Page<Noticia> noticiasPage = noticiaService.findAll(pageRequest);

        int totalPage = noticiasPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }
        List<Noticia> noticias = noticiasPage.getContent();

        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("prev", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("recordsQuantity", (recordsQuantity == Integer.MAX_VALUE ? "Todos" : 8));
        model.addAttribute("sortField", sortField);
        model.addAttribute("orientation", orientation);

        model.addAttribute("noticias", noticias);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        Integer cantidadNotidicacionesNoLeidas = this.notificacionService.obtenerCatidadDeNotificacionesNoLeidas(user);
        model.addAttribute("cantidadNotidicacionesNoLeidas", cantidadNotidicacionesNoLeidas);
        return "home";
    }

    @GetMapping({"/documentacion"})
    public String documentacion(Model model) {
        return "documentation";
    }

    @GetMapping("/obtenerLocalidadUsuario")
    public ResponseEntity<Localidad> getLocalidadUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        Localidad userLocalidad = user.getDireccion().getLocalidad();
        return ResponseEntity.ok().body(userLocalidad);
    }

    @GetMapping("/listarProvincias")
    public ResponseEntity<List<Provincia>> getProvincias() {
        List<Provincia> provs = provinciaService.findAll();
        return ResponseEntity.ok().body(provs);
    }

    @PostMapping("/listarLocalidades/{id}")
    public ResponseEntity<List<Localidad>> getLocalidadesbyProvincia(@PathVariable Long id) {
        List<Localidad> todasLasLocalidades = localidadService.findByProvincia(id);
        return ResponseEntity.ok().body(todasLasLocalidades);
    }

    @PostMapping("/listarCodigoPostal/{id}")
    public ResponseEntity<Localidad> getCpByLocalidad(@PathVariable Long id) {
        Localidad localidad = localidadService.findLocalidadById(id);
        return ResponseEntity.ok().body(localidad);
    }

    @GetMapping("/tripulante/perfil/datos-personales")
    public String profilePersonalData(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userForm = this.userService.findByUsername(authentication.getName());
        Generacion generacion = this.generacionService.findByNumero(userForm.getGeneracion().getNumero());
        List<Localidad> localidades = this.localidadService.findAll();
        List<Provincia> provincias = this.provinciaService.findAll();
        User lider = this.userService.findUserById(userForm.getLider().getId());
        TipoCargo cargo = userForm.getTipoCargo();
        List<Role> roles = this.roleService.obtenerTodosLosRoles();

        model.addAttribute("generacion", generacion);
        model.addAttribute("lider", lider);
        model.addAttribute("cargo", cargo);
        model.addAttribute("roles", roles);
        model.addAttribute("localidades", localidades);
        model.addAttribute("provincias", provincias);
        model.addAttribute("title", "Datos Personales");
        model.addAttribute("userForm", userForm);
        return "personal-data";
    }

    @PostMapping("/tripulante/perfil/datos-personales")
    public String modificarDatosPersonales(@ModelAttribute("userForm") User userForm,
                                           BindingResult bindingResult, Model model) {
        Boolean confirmado = false;
        Generacion generacion = this.generacionService.findByNumero(userForm.getGeneracion().getNumero());
        List<Localidad> localidades = this.localidadService.findAll();
        List<Provincia> provincias = this.provinciaService.findAll();
        User lider = this.userService.findUserById(userForm.getLider().getId());
        TipoCargo cargo = userForm.getTipoCargo();
        List<Role> roles = this.roleService.obtenerTodosLosRoles();
        model.addAttribute("title", "Datos Personales");
        model.addAttribute("generacion", generacion);
        model.addAttribute("lider", lider);
        model.addAttribute("cargo", cargo);
        model.addAttribute("roles", roles);
        model.addAttribute("localidades", localidades);
        model.addAttribute("provincias", provincias);
        model.addAttribute("userForm", userForm);
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        try {
            confirmado = true;
            this.userService.calcularHorasIniciales(userForm);
            userService.update(userForm);
        } catch (Exception e) {
            model.addAttribute(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "personal-data";
        }

        if (confirmado) {
            model.addAttribute("confirmado", "Tus datos fueron actualizados con éxito");
        }
     //   model.addAttribute("title", "Datos Personales");
        return "personal-data";
    }

    @GetMapping("/tripulante/perfil/seguridad")
    public String profileSecurity(Model model) {
        DatosContrasenia datosContrasenia = new DatosContrasenia();
        model.addAttribute("datosContrasenia", datosContrasenia);
        return "security";
    }

    @PostMapping("/newPassword")
    public String registration(@ModelAttribute("datosContrasenia") DatosContrasenia datosContrasenia, BindingResult bindingResult, Model model) {
        Boolean confirmado = false;
        if (bindingResult.hasErrors()) {
            return "security";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        try {
            this.userService.newPassword(user, datosContrasenia);
            userValidator.validate(user, bindingResult);
            confirmado = true;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        if (confirmado == true) {
            try {
                userService.update(user);
                model.addAttribute("confirmado", "Tu contraseña fue actualizada con éxito");
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }

        }

        return "security";
    }

    @GetMapping("/tripulante/perfil/privacidad")
    public String profilePrivacy(Model model) {
        return "privacy";
    }


    @GetMapping("/tripulante/perfil/contacto")
    public String contacto(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("datosContacto", user.getDatosContacto());
        return "contacto";
    }

    @PostMapping("/tripulante/perfil/modificar-contacto")
    public String profileContacto(@RequestParam("userId") Long id, @ModelAttribute("datosContacto") DatosContacto datosContacto, Model model, RedirectAttributes redirectAttributes) {

        User user = this.userService.findUserById(id);
        Boolean confirmado = false;
        try {
            user.setDatosContacto(datosContacto);
            this.userService.update(user);
            confirmado = true;
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        if (confirmado == true) {
            redirectAttributes.addFlashAttribute("confirmado", "Tu dato de contacto fue actualizo con éxito");
        }


        return "redirect:/tripulante/perfil/contacto";
    }

    @GetMapping("/tripulante/perfil/admin")
    public String profileAdmin(Model model) {
        return "admin";
    }
}
