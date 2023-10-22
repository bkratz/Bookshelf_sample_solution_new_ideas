package de.workshops.bookshelf.book;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Book {
    private String title;
    private String isbn;
    private String description;
    private List<Author> authors = new ArrayList<>();
}
