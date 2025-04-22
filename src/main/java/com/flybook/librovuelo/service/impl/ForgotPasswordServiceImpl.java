package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ForgotPasswordService;
import com.flybook.librovuelo.service.UserService;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    @Autowired
    public ForgotPasswordServiceImpl(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Override
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        User user = this.userService.findByMail(recipientEmail);

        helper.setFrom("support@flybondi.com", "Flybondi Support");
        helper.setTo(recipientEmail);

        String subject = "Restablece tu contraseña";
        String content = "<img src='cid:flybondi-logo' alt='flybondi-logo' title='flybondi-logo' style='width: 50px; height: 60px;'>"
                        + "<p> Hola, " + user.getNombre() + "</p>"
                        + "<p>Has solicitado restablecer su contraseña.</p>"
                        + "<p>Haga clic en el siguiente enlace para cambiar su contraseña:</p>"
                        + "<p><a style='color:#ffc107' href=\"" + link + "\">Cambiar mi contraseña</a></p>"
                        + "<br>"
                        + "<p>Ignora este correo si recuerdas tu contraseña, "
                        + "o usted no ha pedido un cambio de contraseña.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        ClassPathResource imageResource = new ClassPathResource("static/img/isotipo.png");
        helper.addInline("flybondi-logo", imageResource);

        mailSender.send(message);
    }



}
