-- The encrypted client_secret it `secret`
-- Lo  client_secret anterior se generó usando  bcrypt . El prefijo  {bcrypt} es obligatorio porque usaremos la nueva característica de Spring Security 5.x de  DelegatingPasswordEncoder .
-- '{bcrypt}$2a$12$zbwa.i5FDsnSebgWHO72OOHhsO6q0cS00ntDIDfHqAOoafLQOv/2C'
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('clientId', '{bcrypt}$2a$12$zbwa.i5FDsnSebgWHO72OOHhsO6q0cS00ntDIDfHqAOoafLQOv/2C', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

-- The encrypted password is `pass`
-- Lo  client_secret anterior se generó usando  bcrypt . El prefijo  {bcrypt} es obligatorio porque usaremos la nueva característica de Spring Security 5.x de  DelegatingPasswordEncoder .
-- '{bcrypt}$2a$12$74SgGir1REovmnsSlijcS.ODWTlAe8c.3zStYSV9lYWvivVZMPbmm'
INSERT INTO users (id, username, password, enabled) VALUES (1, 'user', '{bcrypt}$2a$12$74SgGir1REovmnsSlijcS.ODWTlAe8c.3zStYSV9lYWvivVZMPbmm', 1);
INSERT INTO users (id, username, password, enabled) VALUES (2, 'caramelo', '{bcrypt}$2a$12$j97vmKXIo8PE9ZiQ0Br3V.KII8CCaqiuNZp4bIChZ8.a7aCcIPy1K', 1);
INSERT INTO users (id, username, password, enabled) VALUES (3, 'pass', '{bcrypt}$2a$12$ZlWLYhypcRpSWawNtGc6ieuXX8dV.tbAREq2VBJYfgKF8X1JE5pWm', 1);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('caramelo', 'ROLE_GUEST');
INSERT INTO authorities (username, authority) VALUES ('pass', 'ROLE_GUEST');
