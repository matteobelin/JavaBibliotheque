INSERT INTO genres (name) VALUES
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Biography');

INSERT INTO authors (name) VALUES
('Isaac Asimov'),
('J.K. Rowling'),
('Agatha Christie'),
( 'Walter Isaacson'),
( 'Mickey Mouse');

INSERT INTO books (title, author_id) VALUES
('Foundation',1),
('Harry Potter and the Philosopher s Stone',2),
('Murder on the Orient Express',3),
('Steve Jobs',4),
('Steve Jobs',1);

INSERT INTO users (email, name, isAdmin, password) VALUES
('admin@example.com', 'Admin User', TRUE, 'test'),
('user1@example.com', 'User One', FALSE, 'test'),
('user2@example.com', 'User Two', FALSE, 'test');

INSERT INTO loans (user_id, book_id, start_date, end_date) VALUES
(1, 1, '2025-01-01', '2025-01-15'),
(2, 2, '2025-01-05', '2025-01-20'),
(3, 3, '2025-01-10', '2025-01-25');

INSERT INTO genre_book (genre_id, book_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(4,5),
(3,5);
