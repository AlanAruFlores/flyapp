package com.flybook.librovuelo.conf.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfRestController {

    @GetMapping("/csrf")
    public CsrfToken getCsrfToke(HttpServletRequest request) {

        CsrfToken token =  (CsrfToken) request.getAttribute("_csrf");
        System.out.println(token);
        return token;
    }
}
