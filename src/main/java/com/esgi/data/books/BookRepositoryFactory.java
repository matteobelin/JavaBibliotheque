package com.esgi.data.books;

import com.esgi.data.books.impl.BookRepositoryImpl;

public class BookRepositoryFactory {
    public static BookRepository makeBookRepository() {return new BookRepositoryImpl();}
}
