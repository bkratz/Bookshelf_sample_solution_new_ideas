package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {

    private final BookRepository bookRepository;

    List<Book> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    Book searchBookByIsbn(String isbn) throws BookException {
        return bookRepository.findAllBooks().stream()
                .filter(book -> hasIsbn(book, isbn))
                .findFirst()
                .orElseThrow(BookException::new);
    }

    List<Book> searchBookByAuthor(String author) {
        return bookRepository.findAllBooks().stream()
                .filter(book -> hasAuthor(book, author))
                .toList();
    }

    List<Book> searchBooks(BookSearchRequest request) {
        return bookRepository.findAllBooks().stream()
                .filter(book -> hasAuthor(book, request.author()) || hasIsbn(book, request.isbn()))
                .toList();
    }

    public Book createBook(Book book) {
        books.add(book);

        return book;
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String authorName) {
        return book.getAuthors().stream()
                .anyMatch(author -> author.toString().contains(authorName));
    }
}
