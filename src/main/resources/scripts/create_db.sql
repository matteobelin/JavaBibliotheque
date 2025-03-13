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
                            FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE,
                            FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

CREATE TABLE users (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       email TEXT NOT NULL UNIQUE,
                       name TEXT NOT NULL,
                       isAdmin BOOLEAN NOT NULL DEFAULT 0,
                       password TEXT NOT NULL
);

CREATE TABLE loans (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       user_id INTEGER NOT NULL,
                       book_id INTEGER NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE,
                       FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                       FOREIGN KEY (book_id) REFERENCES books (id)
);