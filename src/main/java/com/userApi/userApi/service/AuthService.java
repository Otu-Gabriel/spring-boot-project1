package com.userApi.userApi.service;

import com.userApi.userApi.dto.LoginRequestDTO;
import com.userApi.userApi.dto.LoginResponseDTO;
import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    KeycloakService keycloakService;
    UserService userService;

//    public AuthService(
//            KeycloakService keycloakService,
//            UserService userService
//    ) {
//        this.keycloakService = keycloakService;
//        this.userService = userService;
//    }

    public UserResponseDTO register(
            UserRequestDTO dto
    ) {

        // validate role exists first
        keycloakService.validateRole(dto.role());

        String keycloakUserId = null;

        try {

            // create user in keycloak
            keycloakUserId =
                    keycloakService.createKeycloakUser(dto);

            // assign role
            keycloakService.assignRoleToUser(
                    keycloakUserId,
                    dto.role()
            );

            // save local user
            return userService.createLocalUser(
                    dto,
                    keycloakUserId
            );

        } catch (Exception ex) {

            if (keycloakUserId != null) {

                try {

                    keycloakService.deleteUser(
                            keycloakUserId
                    );

                } catch (Exception rollbackEx) {

                    log.error(
                            "Rollback failed for Keycloak user {}",
                            keycloakUserId,
                            rollbackEx
                    );
                }
            }

            throw ex;
        }
    }



    public LoginResponseDTO login(
            LoginRequestDTO dto
    ) {
        log.info("call for login came here");
        return keycloakService.login(
                dto.username(),
                dto.password()
        );
    }
}