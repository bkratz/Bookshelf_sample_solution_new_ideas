package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.workshops.bookshelf.config.JacksonTestConfiguration;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(JacksonTestConfiguration.class)
class BookRestControllerIntegrationTest {

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

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
                get(BookRestController.REQUEST_URL).
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
                get(BookRestController.REQUEST_URL).
                then().
                log().all().
                statusCode(200).
                body("size()", is(4));
    }

    @Test
    void createBook() throws Exception {
        String authorFirstname = "Eric";
        String authorLastname = "Evans";
        String title = "Domain-Driven Design: Tackling Complexity in the Heart of Software";
        String isbn = "978-0321125217";
        String description = "This is not a book about specific technologies. It offers readers a systematic approach to domain-driven design, presenting an extensive set of design best practices, experience-based techniques, and fundamental principles that facilitate the development of software projects facing complex domains.";

        final var author = new Author();
        author.setFirstname(authorFirstname);
        author.setLastname(authorLastname);

        Book expectedBook = new Book();
        expectedBook.getAuthors().add(author);
        expectedBook.setTitle(title);
        expectedBook.setIsbn(isbn);
        expectedBook.setDescription(description);

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .content("""
                                {
                                    "isbn": "%s",
                                    "title": "%s",
                                    "description": "%s",
                                    "authors": [
                                        {
                                            "firstname": "%s",
                                            "lastname": "%s"
                                        }
                                    ]
                                }""".formatted(isbn, title, description, authorFirstname, authorLastname))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonPayload = mvcResult.getResponse().getContentAsString();

        Book book = objectMapper.readValue(jsonPayload, Book.class);
        assertEquals(expectedBook, book);
    }
}
