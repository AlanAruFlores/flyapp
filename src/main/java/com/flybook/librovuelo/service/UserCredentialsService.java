package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.UserCredentials;
import com.google.api.client.auth.oauth2.Credential;

public interface UserCredentialsService {
    void save(User user, Credential credential);
    UserCredentials getUserCredentialsByUser(User user);
    void deleteByUser(User user);
    Credential retrieveCredential(User user);
}
