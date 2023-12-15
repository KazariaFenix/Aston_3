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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.service.AuthorService;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {
    @InjectMocks
    private AuthorController controller;
    private AuthorDto authorDto;
    private MockMvc mvc;
    @Mock
    private AuthorService service;
    private String requestBody;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        authorDto = new AuthorDto("Ivan", LocalDate.of(1990, 10, 25), new ArrayList<>());
        requestBody = "{\"name\":\"Ivan\",\"dateBorn\":[1990,10,25],\"booksList\":[]}";
    }

    @Test
    void saveAuthor_whenNormal_thenReturnAuthor() throws Exception {
        Mockito
                .doReturn(authorDto)
                .when(service).addAuthor(authorDto);

        String response = mvc.perform(MockMvcRequestBuilders.post("/authors")
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
    void updateAuthor_whenNormal_thenReturnAuthor() throws Exception {
        Mockito
                .doReturn(authorDto)
                .when(service).updateAuthor(authorDto, 1);

        String response = mvc.perform(MockMvcRequestBuilders.put("/authors/1")
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
    void getAuthor_whenNormal_thenReturnAuthor() throws Exception {
        Mockito
                .doReturn(authorDto)
                .when(service).getAuthorById(1L);
        String response = mvc.perform(MockMvcRequestBuilders.get("/authors/1")
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
    void getAuthors_whenNormal_thenReturnAuthors() throws Exception {
        requestBody = "[" + requestBody + "]";

        Mockito
                .doReturn(List.of(authorDto))
                .when(service).getAuthors();

        String response = mvc.perform(MockMvcRequestBuilders.get("/authors")
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
    void deleteAuthors_whenNormal_thenDeleteAuthor() throws Exception {
        Mockito
                .doNothing()
                .when(service).deleteAuthor(1L);

        mvc.perform(MockMvcRequestBuilders.delete("/authors/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }
}
