package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.UserCredentials;
import com.flybook.librovuelo.repository.UserCredentialsRepository;
import com.flybook.librovuelo.service.UserCredentialsService;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;


    @Override
    public void save(User user, Credential credential) {
        // Extraer información del credential
        String accessToken = credential.getAccessToken();
        String refreshToken = credential.getRefreshToken();  // Puede ser null si no se proporciona un refresh token
        LocalDateTime tokenExpiration = credential.getExpirationTimeMilliseconds() != null ?
                Instant.ofEpochMilli(credential.getExpirationTimeMilliseconds())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime() : null;


        UserCredentials userCredentials = UserCredentials.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiration(tokenExpiration)
                .user(user)
                .createDate(LocalDate.now())
                .build();
        this.userCredentialsRepository.save(userCredentials);
    }

    @Override
    public UserCredentials getUserCredentialsByUser(User user) {
        return this.userCredentialsRepository.findByUser_Id(user.getId());
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        this.userCredentialsRepository.deleteByUser_Id(user.getId());
    }

    @Override
    public Credential retrieveCredential(User user) {
        UserCredentials userCredentials = this.userCredentialsRepository.findByUser_Id(user.getId());

        String accessToken = userCredentials.getAccessToken();
        String refreshToken = userCredentials.getRefreshToken();

        Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod())
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpirationTimeMilliseconds(userCredentials.getTokenExpiration().atZone(
                        ZoneId.systemDefault()).toEpochSecond()
                );

        return credential;

    }
}
