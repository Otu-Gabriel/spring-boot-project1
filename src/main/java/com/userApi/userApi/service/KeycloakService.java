package com.userApi.userApi.service;

import com.userApi.userApi.dto.LoginResponseDTO;
import com.userApi.userApi.dto.UserRequestDTO;
import jakarta.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.userApi.userApi.exception.InvalidRoleException;
import com.userApi.userApi.exception.KeycloakUserCreationException;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    private final Keycloak keycloak;

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

        RealmResource realm =
                keycloak.realm(realmName);

        try {

            realm.roles()
                    .get(roleName)
                    .toRepresentation();

        } catch (Exception ex) {
            log.error("exception occur in validating roles", ex);
            throw new InvalidRoleException(roleName) ;
        }
    }

    public String createKeycloakUser(UserRequestDTO dto) {

        log.info("Creating user in Keycloak");

        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(dto.password());

        UserRepresentation user =
                new UserRepresentation();

        user.setUsername(dto.email());
        user.setEmail(dto.email());
        user.setFirstName(dto.name());
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        RealmResource realm =
                keycloak.realm(realmName);

        Response response =
                realm.users().create(user);

        int status = response.getStatus();

        log.info("Keycloak response status: {}", status);

        if (status != 201) {

            String errorMessage =
                    response.readEntity(String.class);

            response.close();

            throw new KeycloakUserCreationException(
                    errorMessage
            );
        }

        String userId =
                CreatedResponseUtil.getCreatedId(response);

        response.close();

        return userId;
    }

    public void assignRoleToUser( String userId, String roleName ) {

        RealmResource realm =
                keycloak.realm(realmName);

        RoleRepresentation role =
                realm.roles()
                        .get(roleName)
                        .toRepresentation();

        realm.users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    public void deleteUser(String userId) {

        RealmResource realm =
                keycloak.realm(realmName);

        realm.users()
                .delete(userId);
    }

    public LoginResponseDTO login(String username,String password) {

        Keycloak keycloakClient =
                KeycloakBuilder.builder()
                        .serverUrl(serverUrl)
                        .realm(realmName)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .grantType(OAuth2Constants.PASSWORD)
                        .username(username)
                        .password(password)
                        .build();

        AccessTokenResponse tokenResponse =
                keycloakClient.tokenManager()
                        .getAccessToken();

        return LoginResponseDTO.builder()
                .accessToken(tokenResponse.getToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .expiresIn(tokenResponse.getExpiresIn())
                .refreshExpiresIn(
                        tokenResponse.getRefreshExpiresIn()
                )
                .tokenType(tokenResponse.getTokenType())
                .build();
    }
}