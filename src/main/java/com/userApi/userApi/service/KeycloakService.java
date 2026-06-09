package com.userApi.userApi.service;

import com.userApi.userApi.dto.LoginResponseDTO;
import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.exception.*;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realmName;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void validateRole(String roleName) {
        RealmResource realm = keycloak.realm(realmName);
        try {
            realm.roles()
                    .get(roleName)
                    .toRepresentation();
        } catch (Exception ex) {
            log.error("Exception occurred in validating role: {}", roleName, ex);
            throw new InvalidRoleException(roleName);
        }
    }

    public String createKeycloakUser(UserRequestDTO dto) {
        log.info("Creating user in Keycloak: {}", dto.email());

        UserRepresentation user = prepareKeycloakUser(dto);

        RealmResource realm = keycloak.realm(realmName);

        try (Response response = realm.users().create(user)) {
            int status = response.getStatus();
            log.info("Keycloak response status: {}", status);

            if (status != 201) {
                String errorMessage = response.readEntity(String.class);
                throw new KeycloakUserCreationException(errorMessage);
            }

            return CreatedResponseUtil.getCreatedId(response);
        }
    }

    private static UserRepresentation prepareKeycloakUser(UserRequestDTO dto) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.password());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(dto.email());
        user.setEmail(dto.email());
        user.setFirstName(dto.name());
        user.setLastName("user");
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCredentials(List.of(credential));
        user.setRequiredActions(List.of());
        return user;
    }

    public void assignRoleToUser(String userId, String roleName) {
        RealmResource realm = keycloak.realm(realmName);

        RoleRepresentation role = realm.roles()
                .get(roleName)
                .toRepresentation();

        realm.users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    public void deleteUser(String userId) {
        RealmResource realm = keycloak.realm(realmName);
        realm.users().delete(userId);
    }

    public LoginResponseDTO login(String username, String password) {
        log.info("Attempting login authentication for user: {}", username);

        try (Keycloak keycloakClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.PASSWORD)
                .username(username)
                .password(password)
                .build()) {

            AccessTokenResponse tokenResponse;

            try {
                tokenResponse = keycloakClient.tokenManager().getAccessToken();
                log.info("Successfully authenticated user: {}", username);

            } catch (WebApplicationException ex) {
                Response response = ex.getResponse();
                int statusCode = response != null ? response.getStatus() : 500;
                String errorBody = "";

                try {
                    if (response != null && response.hasEntity()) {
                        errorBody = response.readEntity(String.class);
                    }
                } catch (Exception entityEx) {
                    log.error("Failed to read error response body from Keycloak", entityEx);
                }

                log.error("Keycloak authentication failed for user: {}. Status Code: {}. Error Details: {}",
                        username, statusCode, errorBody, ex);

                if (statusCode == 401) {
                    throw new BadCredentialsException("Invalid username or password");
                } else if (statusCode == 400) {
                    throw new InvalidLoginRequestException("Invalid request formatting or disabled user: " + errorBody);
                } else {
                    throw new KeycloakAuthenticationException("Keycloak server error during login status: " + statusCode);
                }

            } catch (Exception ex) {
                log.error("Unexpected non-HTTP exception occurred during login for user: {}", username, ex);
                throw new LoginServiceException("An unexpected internal error occurred during authentication", ex);
            }

            return LoginResponseDTO.builder()
                    .accessToken(tokenResponse.getToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .expiresIn(tokenResponse.getExpiresIn())
                    .refreshExpiresIn(tokenResponse.getRefreshExpiresIn())
                    .tokenType(tokenResponse.getTokenType())
                    .build();
        }

    }
}