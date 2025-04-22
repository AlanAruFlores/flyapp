package com.flybook.librovuelo.web;

import com.flybook.librovuelo.exceptions.UserNotFoundException;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ForgotPasswordService;
import com.flybook.librovuelo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
public class ForgotPasswordController {


    private final UserService userService;
    private final ForgotPasswordService forgotPasswordService;

    @Autowired
    public ForgotPasswordController(UserService userService, ForgotPasswordService forgotPasswordService) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        try {
            this.userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            this.forgotPasswordService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "Hemos enviado un enlace para restablecer la contraseña a tu correo electrónico. Por favor, comprobalo.");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error al enviar el mail, por favor, intentá de nuevo");
        }

        return "forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = this.userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Token invalido");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = this.userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Cambia tu contraseña");

        try {
            if (user == null) {
                model.addAttribute("message", "Token invalido");
                return "reset_password_form";
            } else {
                this.userService.updatePassword(user, password);
                model.addAttribute("message", "Tu contraseña se cambio con exito");
            }
        } catch (Exception e) {
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            redirectAttributes.addFlashAttribute("invalidPassword", e.getMessage());
            return "redirect:" + resetPasswordLink;
        }


        return "reset_password_form";
    }

}