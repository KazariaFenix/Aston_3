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
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.marzuev.aston.model.dto.AuthorDto;
import ru.marzuev.aston.model.dto.BookDto;
import ru.marzuev.aston.model.dto.CommentDto;
import ru.marzuev.aston.service.BookService;
import ru.marzuev.aston.service.CommentService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @InjectMocks
    private CommentController controller;
    @Mock
    private CommentService service;
    private BookDto bookDto;
    private CommentDto commentDto;
    private MockMvc mvc;
    private String requestBody;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
        commentDto = new CommentDto("ArtOfWar", "Content");
    }

    @Test
    void saveComment_whenNormal_thenReturnComment() throws Exception {
        Mockito
                .doReturn(commentDto)
                .when(service).addComment(commentDto, 1L);

        String response = mvc.perform(MockMvcRequestBuilders.post("/comments")
                        .param("bookId", "1")
                        .content(mapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(mapper.writeValueAsString(commentDto), response);
    }

    @Test
    void saveComments_whenBookNotFound_throwException() throws Exception {
        Mockito
                .doReturn(commentDto)
                .when(service).updateComment(commentDto, 1L);

        String response = mvc.perform(MockMvcRequestBuilders.put("/comments/1")
                        .content(mapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(mapper.writeValueAsString(commentDto), response);
    }

    @Test
    void deleteComment_whenNormal_thenDeleteComment() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/comments/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }

    @Test
    void getCommentsByBookId_whenNormal_thenReturnComments() throws Exception {
        Mockito
                .doReturn(List.of(commentDto))
                .when(service).getCommentsByBookId(1L);

        String response = mvc.perform(MockMvcRequestBuilders.get("/comments/1")
                        .content(mapper.writeValueAsString(commentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(mapper.writeValueAsString(List.of(commentDto)), response);
    }
}
