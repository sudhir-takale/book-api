package com.amaap.book.controller;

import com.amaap.book.entity.Book;
import com.amaap.book.service.BookService;
import com.amaap.book.service.exception.BookNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @MockBean
    BookService bookService;


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldBeAbleToCreateNewBook() throws Exception {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        when(bookService.createBook(book)).thenReturn(book);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                                 .post("/api/v1/book")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Clean code")))
                .andExpect(jsonPath("$.author",is("uncle bob")));

    }


    @Test
    void shouldBeAbleToGetAllBook() throws Exception {
        Book book = new Book("Clean code", "uncle bob", "XYZ", 34.3);
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookService.getBooks()).thenReturn(books);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                                 .get("/api/v1/books")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(asJsonString(book)))
                .andExpect(jsonPath("$",isA(List.class)))
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].author", is("uncle bob")))
                .andExpect(status().isOk());
    }


    @Test
    void shouldBeAbleToGetABookByName() throws Exception, BookNotFoundException {
        Book book = new Book("Design Pattern", "uncle bob", "XYZ", 34.3);
        when(bookService.getBook("Design Pattern")).thenReturn(book);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                                 .get("/api/v1/book/Design Pattern")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(asJsonString(book)))
                .andExpect(jsonPath("$.name", is("Design Pattern")))
                .andExpect(jsonPath("$.author",is("uncle bob")))
                .andExpect(status().isOk());

    }

    @Test
    void shouldAbleToDeleteBookByIdIfBookNotFoundReturnNotFound() throws Exception, BookNotFoundException {
        when(bookService.removeBook("Design pattern")).thenThrow(new BookNotFoundException("Book not found with Design pattern"));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                                 .delete("/api/v1/book/delete/Design pattern")
                                 .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldBeAbleToUpdateBookPrice() throws Exception, BookNotFoundException {
        Book book = new Book("Design Pattern", "uncle bob", "XYZ", 34.3);
        when(bookService.update("Design pattern", "ABC")).thenReturn(null);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                                 .patch("/api/v1/book/update/Design pattern")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .param("publisher", "ABC")
                                 .content(asJsonString(book)))
                .andExpect(status().isOk());
    }


}