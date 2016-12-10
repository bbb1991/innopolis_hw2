-- Создание структуры таблицы

CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY,
  username VARCHAR(32)  NOT NULL,
  password VARCHAR(255) NOT NULL,
  blocked  BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS books (
  id        SERIAL PRIMARY KEY,
  title     VARCHAR(255) NOT NULL,
  author_id INT          NOT NULL,
  date      TIMESTAMP DEFAULT now(),
  content   TEXT         NOT NULL,
  draft     BOOLEAN   DEFAULT FALSE,
  FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY,
  name VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Тестовые данные

INSERT INTO users (username, password)
VALUES ('admin', '$2a$11$BYC7YD1L/bHutHPEkgclVemSgF7Y.CnltGEn1LUBNd5XbnqZXahS6');
INSERT INTO users (username, password)
VALUES ('plain_user', '$2a$11$OaF46r/E/URUJmQAn0jgVO.Hb5RHSczw3hBcx3ve/Aj8SrEQL.d9.');
INSERT INTO users (username, password)
VALUES ('blocked_user', '$2a$11$DuyzQdi44MJ9HAH9yn4tv.U2dcwr/L2HSaTkeGEDs.aUlom06W9eW');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO books (title, author_id, content) VALUES ('Title1', 1, 'Some useless content');
INSERT INTO books (title, author_id, content) VALUES ('Title2', 2, 'Some useless content');
INSERT INTO books (title, author_id, content) VALUES ('Title3', 1, 'Some useless content');