package com.userApi.userApi.exception;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String role) {
        super("Role '" + role + "' does not exist in Keycloak");
    }
}