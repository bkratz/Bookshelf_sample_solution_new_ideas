package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {

    private final BookRepository bookRepository;

    List<Book> getBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
            .toList();
    }

    Book getSingleBook(String isbn) throws BookException {
        return getBooks().stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(BookException::new);
    }

    Book searchBookByAuthor(String author) throws BookException {
        return getBooks().stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(BookException::new);
    }

    List<Book> searchBooks(BookSearchRequest request) {
        return getBooks().stream()
                .filter(book -> hasAuthor(book, request.author()))
                .filter(book -> hasIsbn(book, request.isbn()))
                .toList();
    }

    Book createBook(Book book) {
        bookRepository.save(book);

        return book;
    }

    void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
