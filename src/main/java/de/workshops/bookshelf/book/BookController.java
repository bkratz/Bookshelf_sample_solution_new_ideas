package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookController {

    private final ObjectMapper mapper;

    private List<Book> books;

    public BookController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() throws IOException {
        this.books = mapper.readValue(new File("target/classes/books.json"), new TypeReference<>() {});
    }
    
    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", books);

        return "books";
    }
}
