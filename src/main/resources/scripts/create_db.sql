CREATE TABLE authors (
id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL
);

CREATE TABLE genres (
id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL
);

CREATE TABLE books (
id INTEGER PRIMARY KEY AUTOINCREMENT,
title TEXT NOT NULL,
author_id INTEGER,
FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE genre_book (
                            genre_id INTEGER,
                            book_id INTEGER,
                            PRIMARY KEY (genre_id, book_id),
                            FOREIGN KEY (genre_id) REFERENCES genres (id),
                            FOREIGN KEY (book_id) REFERENCES books (id)
);

CREATE TABLE users (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       email TEXT NOT NULL UNIQUE,
                       name TEXT NOT NULL,
                       isAdmin BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE loans (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       user_id INTEGER NOT NULL,
                       book_id INTEGER NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE,
                       FOREIGN KEY (user_id) REFERENCES users (id),
                       FOREIGN KEY (book_id) REFERENCES books (id)
);