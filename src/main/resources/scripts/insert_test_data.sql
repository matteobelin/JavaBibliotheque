INSERT INTO genres (id, name) VALUES
(1, 'Science Fiction'),
(2, 'Fantasy'),
(3, 'Mystery'),
(4, 'Biography');

INSERT INTO authors (id, name) VALUES
(1, 'Isaac Asimov'),
(2, 'J.K. Rowling'),
(3, 'Agatha Christie'),
(4, 'Walter Isaacson');

INSERT INTO books (id, title, author_id) VALUES
(1, 'Foundation',1),
(2, 'Harry Potter and the Philosopher s Stone',2),
(3, 'Murder on the Orient Express',3),
(4, 'Steve Jobs',4);

INSERT INTO users (id, email, name, isAdmin, password) VALUES
(1, 'admin@example.com', 'Admin User', TRUE, 'test'),
(2, 'user1@example.com', 'User One', FALSE, 'test'),
(3, 'user2@example.com', 'User Two', FALSE, 'test');

INSERT INTO loans (id, user_id, book_id, start_date, end_date) VALUES
(1, 1, 1, '2025-01-01', '2025-01-15'),
(2, 2, 2, '2025-01-05', '2025-01-20'),
(3, 3, 3, '2025-01-10', '2025-01-25');

INSERT INTO genre_book (genre_id, book_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
