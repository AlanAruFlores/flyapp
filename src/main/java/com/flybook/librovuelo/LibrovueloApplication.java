package com.flybook.librovuelo;

import com.flybook.librovuelo.conf.EnvironmentUtils;
import com.flybook.librovuelo.conf.SpringConfig;
import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalTime;


@SpringBootApplication
@EnableScheduling
public class LibrovueloApplication {

    public static void main(String[] args) {
        String profile = EnvironmentUtils.getSpringProfile();
        new SpringApplicationBuilder(SpringConfig.class)
                .profiles(profile)
                .registerShutdownHook(true).run(args);
    }
}
