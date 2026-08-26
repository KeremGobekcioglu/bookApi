package com.firstRestApi.demo.controllers;

import com.firstRestApi.demo.TestDataUtil;
import com.firstRestApi.demo.domain.dto.BookDto;
import com.firstRestApi.demo.domain.entities.BookEntity;
import com.firstRestApi.demo.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Autowired
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturns201() throws Exception
    {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatCreateBookReturnsBook() throws Exception
    {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatGetBooksReturn200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnListofBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(book.getIsbn(),book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn())
        );
    }

    @Test
    public void testThatGetBookReturn200WhenBookExists() throws Exception {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(book.getIsbn(),book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatGetBookReturn404WhenBookDontExist() throws Exception {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnBookWhenExists() throws Exception
    {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(book.getIsbn(),book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        );
    }

    @Test
    public void testThatUpdateBookReturns200() throws Exception
    {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(book.getIsbn(),book);
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception
    {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(book.getIsbn(),book);
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBookEntity.getIsbn());
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }
}
