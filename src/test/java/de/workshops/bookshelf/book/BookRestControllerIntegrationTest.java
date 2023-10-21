package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class BookRestControllerIntegrationTest {

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/book"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].title", hasItem("Clean Code")))
                .andReturn();

        String jsonPayload = mvcResult.getResponse().getContentAsString();

        List<Book> books = objectMapper.readValue(jsonPayload, new TypeReference<>() {});
        assertThat(books).hasSize(4)
                .anyMatch(book -> book.getTitle().equals("Clean Code"));
    }

    @Test
    void testWithRestAssuredMockMvc() {
        RestAssuredMockMvc.standaloneSetup(bookRestController);
        final var response = RestAssuredMockMvc.
                given().
                log().all().
                when().
                get("/book").
                then().
                log().all().
                statusCode(200).
                body("size()", is(4)).
                extract().response();
        final var books = response.as(new TypeRef<List<Book>>() {});
        assertThat(books)
                .anyMatch(book -> book.getAuthors().stream()
                        .anyMatch(author -> author.getFirstname().equals("Erich")));
    }

    @Test
    void testWithRestAssured() {
        RestAssured.
                given().
                log().all().
                when().
                get("/book").
                then().
                log().all().
                statusCode(200).
                body("size()", is(4));
    }

    @Test
    void createBook() throws Exception {
        String author = "Eric Evans";
        String title = "Domain-Driven Design: Tackling Complexity in the Heart of Software";
        String isbn = "978-0321125217";
        String description = "This is not a book about specific technologies. It offers readers a systematic approach to domain-driven design, presenting an extensive set of design best practices, experience-based techniques, and fundamental principles that facilitate the development of software projects facing complex domains.";

        Book expectedBook = new Book();
        expectedBook.setAuthor(author);
        expectedBook.setTitle(title);
        expectedBook.setIsbn(isbn);
        expectedBook.setDescription(description);

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "author": "%s",
                                    "description": "%s"
                                }""".formatted(isbn, title, author, description))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonPayload = mvcResult.getResponse().getContentAsString();

        Book book = objectMapper.readValue(jsonPayload, Book.class);
        assertEquals(expectedBook, book);
    }
}
