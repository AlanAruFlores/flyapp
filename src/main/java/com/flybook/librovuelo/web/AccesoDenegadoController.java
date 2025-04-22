package com.flybook.librovuelo.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccesoDenegadoController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @GetMapping("/accesodenegado")
    public String getAccessDenied() {
        return "accesodenegado";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status =
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            String message = "";
            String title = "";
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                title = "Página no encontrada";
                message = "La página a la que deseas acceder no se encuentra disponible o no existe.";
                model.addAttribute("title", title);
                model.addAttribute("status", statusCode);
                model.addAttribute("message", message);
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                title = "Fallo inesperado";
                message = "No se pudo completar la solicitud al servidor.";
                model.addAttribute("title", title);
                model.addAttribute("status", statusCode);
                model.addAttribute("message", message);
                return "error-500";
            }
        }
        return "errorPage";
    }

    public String getErrorPath() {
        return ERROR_PATH ;
    }
}

