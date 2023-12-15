package ru.marzuev.aston.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.service.BookService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @InjectMocks
    private BookController controller;
    @Mock
    private BookService service;
    private BookDto bookDto;
    private AuthorDto authorDto;
    private MockMvc mvc;
    private String requestBody;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        authorDto = new AuthorDto("Ivan", LocalDate.of(1990, 10, 25), new ArrayList<>());
        bookDto = new BookDto("ArtOfWar", "Description", LocalDate.of(1990, 10, 25),
                List.of(authorDto), new ArrayList<>());
        requestBody = "{\"title\":\"ArtOfWar\",\"description\":\"Description\",\"release\":[1990,10,25]," +
                "\"listAuthors\":[{\"name\":\"Ivan\",\"dateBorn\":[1990,10,25],\"booksList\":[]}],\"listComments\":[]}";
    }

    @Test
    void saveBook_whenNormal_thenReturnBook() throws Exception {
        Mockito
                .doReturn(bookDto)
                .when(service).addBook(bookDto, List.of(1L));

        String response = mvc.perform(MockMvcRequestBuilders.post("/books")
                        .param("authors", "1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(requestBody, response);
    }

    @Test
    void updateBook_whenNormal_thenReturnBook() throws Exception {
        Mockito
                .doReturn(bookDto)
                .when(service).updateBook(bookDto, 1L);

        String response = mvc.perform(MockMvcRequestBuilders.put("/books/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(requestBody, response);
    }

    @Test
    void getBookById_whenNormal_thenReturnBook() throws Exception {
        Mockito
                .doReturn(bookDto)
                .when(service).getBookById(1L);

        String response = mvc.perform(MockMvcRequestBuilders.get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(requestBody, response);
    }

    @Test
    void getBooksByAuthorId_whenNormal_thenReturnBooks() throws Exception {
        requestBody = "[" + requestBody + "]";

        Mockito
                .doReturn(List.of(bookDto))
                .when(service).getBooksByAuthorId(1L);

        String response = mvc.perform(MockMvcRequestBuilders.get("/books")
                        .param("authorId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(requestBody, response);
    }

    @Test
    void deleteBooks_whenNormal_thenDeleteAuthor() throws Exception {
        Mockito
                .doNothing()
                .when(service).deleteBook(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }
}
