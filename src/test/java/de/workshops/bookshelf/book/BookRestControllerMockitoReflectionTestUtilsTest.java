package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRestControllerMockitoReflectionTestUtilsTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookRestController bookRestController;

    @Test
    void getAllBooks() {
        ReflectionTestUtils.setField(bookRepository, "books", Collections.emptyList());

        assertEquals(0, bookRestController.getAllBooks().size());
    }
}
