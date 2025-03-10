INSERT INTO genres (name) VALUES
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Biography');

INSERT INTO authors (name) VALUES
('Isaac Asimov'),
('J.K. Rowling'),
('Agatha Christie'),
( 'Walter Isaacson');

INSERT INTO books (title, author_id) VALUES
('Foundation',1),
('Harry Potter and the Philosopher s Stone',2),
('Murder on the Orient Express',3),
('Steve Jobs',4);

INSERT INTO genre_book (genre_id, book_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);