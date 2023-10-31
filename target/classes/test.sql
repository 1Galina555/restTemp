BEGIN;

DROP TABLE IF EXISTS authors CASCADE;

DROP TABLE IF EXISTS libraries CASCADE;

DROP TABLE IF EXISTS books CASCADE;

DROP TABLE IF EXISTS libraries_authors CASCADE;

CREATE TABLE libraries (id BIGSERIAL primary key, title VARCHAR(50));

INSERT INTO libraries (title)
VALUES ('Sbook'),
       ('Public'),
       ('Smartra'),
       ('Libras');

CREATE TABLE authors (id BIGSERIAL primary key, name VARCHAR(50), lastname VARCHAR(50));

INSERT INTO authors (name, lastname)
VALUES ('Stephen', 'King'),
       ('Alexander', 'Pushkin'),
       ('Mikhail', 'Lermontov'),
       ('Agatha', 'Christie');

CREATE TABLE books (
                       id BIGSERIAL primary key,
                       title VARCHAR(29),
                       genre VARCHAR(15),
                       library_id BIGINT REFERENCES books (id) ON DELETE CASCADE,
                       author_id BIGINT REFERENCES authors (id) ON DELETE CASCADE

);

INSERT INTO books (title,genre, library_id, author_id)
VALUES ('Лунный камень','Детектив', 1, 1),
       ('Ребекка','Детектив', 1, 2),
       ('Тайна отца Брауна','Детектив', 1, 3),
       ('Если бы смерть спала','Детектив', 2, 4);


CREATE TABLE libraries_authors (
                               library_id BIGINT,
                               author_id BIGINT,
                               foreign key (library_id) REFERENCES libraries(id) ON DELETE CASCADE,
                               foreign key (author_id) REFERENCES authors(id) ON DELETE CASCADE
);

INSERT INTO libraries_authors (library_id, author_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 4);

COMMIT;

