package com.flybook.librovuelo.service;

import com.flybook.librovuelo.exceptions.UserNotFoundException;
import com.flybook.librovuelo.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface ForgotPasswordService {

    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
}
