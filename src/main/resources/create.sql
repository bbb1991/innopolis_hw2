CREATE TABLE users (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR2(32)  NOT NULL,
  password VARCHAR2(255) NOT NULL,
  blocked  BOOLEAN         DEFAULT FALSE
);

CREATE TABLE books (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  title     VARCHAR2(255) NOT NULL,
  author_id INT           NOT NULL,
  date      TIMESTAMP       DEFAULT SYSTIMESTAMP,
  content   TEXT          NOT NULL,
  FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE roles (
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR2(60)
);

CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users (username, password) VALUES ('admin', '$2a$11$4c9jr3hajkKXmGEuFY11guYHCOfDsT2RTbWoiBeYFuJ0fmQLpnsLK');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO books (title, author_id, content) VALUES ('Title1', 1, 'Some useless content');