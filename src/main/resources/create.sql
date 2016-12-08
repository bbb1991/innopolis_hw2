CREATE TABLE author (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR2(32)  NOT NULL,
  password VARCHAR2(255) NOT NULL,
  blocked  BOOLEAN         DEFAULT FALSE
);

CREATE TABLE book (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  title     VARCHAR2(255) NOT NULL,
  author_id INT           NOT NULL,
  date      TIMESTAMP       DEFAULT SYSTIMESTAMP,
  content   TEXT          NOT NULL,
  FOREIGN KEY (author_id) REFERENCES author (id)
);

CREATE TABLE role (
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR2(60)
);

CREATE TABLE user_role (
  user_id int NOT NULL ,
  role_id int NOT NULL ,

  FOREIGN KEY (user_id) REFERENCES author(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);

INSERT INTO author (username, password) VALUES ('admin', 'admin');

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO book (title, author_id, content) VALUES ('Title1', 1, 'Some useless content');