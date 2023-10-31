BEGIN;

CREATE TABLE IF NOT EXISTS libraries (id BIGSERIAL primary key, title VARCHAR(50));

CREATE TABLE IF NOT EXISTS authors (id BIGSERIAL primary key, name VARCHAR(50), lastname VARCHAR(50));

CREATE TABLE IF NOT EXISTS books (
                                     id BIGSERIAL primary key,
                                     title VARCHAR(29),
                                     genre VARCHAR(15),
    library_id BIGINT REFERENCES libraries (id) ON DELETE CASCADE,
    author_id BIGINT REFERENCES authors (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS libraries_authors (
                                             library_id BIGINT,
                                             author_id BIGINT,
                                             foreign key (library_id) REFERENCES libraries(id) ON DELETE CASCADE,
    foreign key (author_id) REFERENCES authors(id) ON DELETE CASCADE
    );

COMMIT;