package com.esgi.presentation.utils;

import com.esgi.domain.books.BookEntity;
import com.esgi.domain.genres.GenreEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BookUtils {

    public static List<String> makeBookTable(List<BookEntity> books) {
        var tableHeader = List.of(
                "#",
                "Id",
                "Title",
                "Author",
                "Genres"
        );
        var tableRows = new ArrayList<List<String>>();
        tableRows.add(tableHeader);

        for (int i = 0; i < books.size(); i++) {
            var book = books.get(i);
            var tableRow = mapBookToTableRow(i + 1, book);
            tableRows.add(tableRow);
        }

        return StringUtils.makeTable(tableRows);
    }

    private static List<String> mapBookToTableRow(Integer index, BookEntity book) {
        return List.of(
                index.toString(),
                String.valueOf(book.getId()),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenres().stream().map(GenreEntity::getName).collect(Collectors.joining(", "))
        );
    }

}
