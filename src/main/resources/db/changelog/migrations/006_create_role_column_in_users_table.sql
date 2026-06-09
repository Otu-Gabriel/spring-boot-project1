ALTER TABLE users
ADD COLUMN role VARCHAR(30),
ADD COLUMN keycloakId VARCHAR(100);

CREATE UNIQUE INDEX uq_users_keycloak_id
ON users(keycloakId);