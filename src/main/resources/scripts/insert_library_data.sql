INSERT INTO genres (name) VALUES
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Biography'),
('Historical Fiction'),
('Horror'),
('Non-Fiction'),
('Dystopian'),
('Thriller'),
('Philosophy'),
('Adventure');

INSERT INTO authors (name) VALUES
('Isaac Asimov'),
('J.K. Rowling'),
('Agatha Christie'),
('Walter Isaacson'),
('George Orwell'),
('Stephen King'),
('Yuval Noah Harari'),
('Aldous Huxley'),
('J.R.R. Tolkien'),
('Arthur Conan Doyle'),
('Dan Brown'),
('Frank Herbert'),
('Mary Shelley'),
('Fyodor Dostoevsky'),
('H.G. Wells');

INSERT INTO books (title, author_id) VALUES
('Foundation',1),
('Harry Potter and the Philosopher s Stone',2),
('Murder on the Orient Express',3),
('Steve Jobs',4),
('1984', 5),
('The Shining', 6),
('Sapiens: A Brief History of Humankind', 7),
('Brave New World', 8),
('The Hobbit', 9),
('Dune', 12),
('Dracula', 13),
('Crime and Punishment', 14),
('The Time Machine', 15),
('The Lord of the Rings', 9),
('Sherlock Holmes: A Study in Scarlet', 10),
('The Da Vinci Code', 11),
('It', 6),
('The War of the Worlds', 15),
('Frankenstein', 13);

INSERT INTO genre_book (genre_id, book_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(8, 5),
(6, 6),
(11, 9),
(2, 14),
(7, 7),
(2, 9),
(11, 18),
(6, 19),
(11, 13),
(1, 18),
(1, 10),
(4, 4);