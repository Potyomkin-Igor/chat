CREATE SCHEMA IF NOT EXISTS thymeleaf;

CREATE TABLE IF NOT EXISTS thymeleaf.users (
  id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name  VARCHAR(50),
  password   VARCHAR(150) NOT NULL,
  message_id INT          NULL,
  email      VARCHAR(50)  NOT NULL,
  photo      OID          NULL,
  CONSTRAINT USER_MESSAGES_FK
  FOREIGN KEY (message_id) REFERENCES thymeleaf.messages (id)
  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS thymeleaf.roles (
  id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS thymeleaf.users_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT USER_ROLE_FK FOREIGN KEY (user_id)
  REFERENCES thymeleaf.users (id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT ROLE_FK FOREIGN KEY (role_id)
  REFERENCES thymeleaf.roles (id)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS thymeleaf.messages (
  id   INT     NOT NULL PRIMARY KEY AUTO_INCREMENT,
  text VARCHAR NULL
);

CREATE TABLE IF NOT EXISTS thymeleaf.password_reset_tokens (
  id      INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  token   VARCHAR NOT NULL,
  expire_time TIME NULL,
  user_id INT     NOT NULL,
  CONSTRAINT USER_TOKEN_FK FOREIGN KEY (user_id)
  REFERENCES thymeleaf.users (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);

