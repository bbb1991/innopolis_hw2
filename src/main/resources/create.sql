-- Создание структуры таблицы

-- Таблица для пользователей
CREATE TABLE IF NOT EXISTS users (
  id       SERIAL PRIMARY KEY, -- порядковый номер в БД
  username VARCHAR(32)  NOT NULL, -- логин пользовтеля
  password VARCHAR(255) NOT NULL, -- хеш пароля
  blocked  BOOLEAN DEFAULT FALSE, -- заблокирован ли пользователь
  avatar   VARCHAR(255) -- путь до файла аватарки
);

-- Таблица для книги
CREATE TABLE IF NOT EXISTS books (
  id        SERIAL PRIMARY KEY, -- порядковый номер в БД
  title     VARCHAR(255) NOT NULL, -- Название книги
  author_id INT          NOT NULL, -- кто загрузил данную книгу
  date      TIMESTAMP DEFAULT now(), -- время создания
  content   TEXT         NOT NULL, -- содержание книги
  draft     BOOLEAN   DEFAULT FALSE, -- отметка, черновик ли, или данная книга готова для публикаций
  FOREIGN KEY (author_id) REFERENCES users (id)
);

-- Таблица с ролями в системе
CREATE TABLE IF NOT EXISTS roles (
  id   SERIAL PRIMARY KEY, -- Порядковый номер в БД
  name VARCHAR(60) -- Названия роли
);

-- Связка многое ко многим пользователей и ролей
CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Комментарий к книгам
CREATE TABLE IF NOT EXISTS comments (
  id      SERIAL PRIMARY KEY, -- Порядковый номер в БД
  book_id INT NOT NULL, -- На какую книгу дана комментарий
  user_id INT NOT NULL, -- кто написал комментарий
  rating  FLOAT DEFAULT 0, -- рейтинг
  title   VARCHAR(255), -- короткое название комментария
  body    TEXT, -- содержание комментария
  date TIMESTAMP DEFAULT now(),

  FOREIGN KEY (book_id) REFERENCES books (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Тестовые данные
INSERT INTO users (username, password)
VALUES ('admin', '$2a$11$BYC7YD1L/bHutHPEkgclVemSgF7Y.CnltGEn1LUBNd5XbnqZXahS6');
INSERT INTO users (username, password)
VALUES ('plain_user', '$2a$11$OaF46r/E/URUJmQAn0jgVO.Hb5RHSczw3hBcx3ve/Aj8SrEQL.d9.');
INSERT INTO users (username, password, blocked)
VALUES ('blocked_user', '$2a$11$DuyzQdi44MJ9HAH9yn4tv.U2dcwr/L2HSaTkeGEDs.aUlom06W9eW', TRUE);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO books (title, author_id, content) VALUES ('Book with comment', 1, 'Some useless content');
INSERT INTO books (title, author_id, content) VALUES ('Book without comment1', 2, 'Some useless content');
INSERT INTO books (title, author_id, content) VALUES ('Book without comment2', 1, 'Some useless content');

INSERT INTO comments (book_id, user_id, rating, title, body)
VALUES (1, 1, 5, 'Useless comment title', 'Some useless comment');