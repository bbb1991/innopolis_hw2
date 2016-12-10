CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY,
  username VARCHAR(32)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  blocked  BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS books (
  id        SERIAL PRIMARY KEY ,
  title     VARCHAR(255) NOT NULL,
  author_id INT          NOT NULL,
  date      TIMESTAMP DEFAULT now(),
  content   TEXT         NOT NULL,
  FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY ,
  name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users (username, password) VALUES ('admin', '$2a$11$BYC7YD1L/bHutHPEkgclVemSgF7Y.CnltGEn1LUBNd5XbnqZXahS6');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO books (title, author_id, content) VALUES ('Title1', 1, 'Some useless content');