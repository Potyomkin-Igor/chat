CREATE SCHEMA IF NOT EXISTS thymeleaf;

CREATE TABLE IF NOT EXISTS thymeleaf.roles (
  id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NULL
);

CREATE TABLE IF NOT EXISTS thymeleaf.users (
  id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name  VARCHAR(50),
  password   VARCHAR(150) NOT NULL,
  email      VARCHAR(50)  NOT NULL,
  photo      OID          NULL,
);

CREATE TABLE IF NOT EXISTS thymeleaf.messages (
  id      BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
  message VARCHAR NULL,
  user_id BIGINT  NOT NULL,
  CONSTRAINT USER_MESSAGE_FK FOREIGN KEY (user_id) REFERENCES thymeleaf.users (id)
  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS thymeleaf.users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT USER_ROLE_FK FOREIGN KEY (user_id)
  REFERENCES thymeleaf.users (id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT ROLE_FK FOREIGN KEY (role_id)
  REFERENCES thymeleaf.roles (id)
  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS thymeleaf.password_reset_tokens (
  id          BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
  token       VARCHAR NOT NULL,
  expire_time DATE    NULL,
  user_id     BIGINT  NOT NULL,
  CONSTRAINT USER_TOKEN_FK FOREIGN KEY (user_id)
  REFERENCES thymeleaf.users (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);

