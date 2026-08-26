package com.firstRestApi.demo.controllers;


import com.firstRestApi.demo.TestDataUtil;
import com.firstRestApi.demo.domain.dto.AuthorDto;
import com.firstRestApi.demo.domain.entities.AuthorEntity;
import com.firstRestApi.demo.services.AuthorService;
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
public class AuthorControllerIntegrationTests {
    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper; /// we need it beacuse we will turn our entity to json.


    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorEndpointReturns201() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorEndpointReturnsAuthor() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("80")
        );
    }

    @Test
    public void testThatGetAuthorsReturn200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorsReturnListofAuthors() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // id must be nulled: TestDataUtil hardcodes id=1, which makes save() merge()
        // an update against a nonexistent row instead of persist()-ing a new one,
        // throwing ObjectOptimisticLockingFailureException ("row already updated/deleted").
        author.setId(null);
        authorService.saveAuthor(author);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value("80")
        );
    }

    @Test
    public void testThatGetAuthorReturn200WhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // id must be nulled: TestDataUtil hardcodes id=1, which makes save() merge()
        // an update against a nonexistent row instead of persist()-ing a new one,
        // throwing ObjectOptimisticLockingFailureException ("row already updated/deleted").
        author.setId(null);
        authorService.saveAuthor(author);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturn404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorReturnAuthorWhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // id must be nulled: TestDataUtil hardcodes id=1, which makes save() merge()
        // an update against a nonexistent row instead of persist()-ing a new one,
        // throwing ObjectOptimisticLockingFailureException ("row already updated/deleted").
        author.setId(null);
        authorService.saveAuthor(author);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturn404WhenNoAuthorExists() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String dtoJson = objectMapper.writeValueAsString(authorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturn200WhenAuthorExists() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // id must be nulled: TestDataUtil hardcodes id=1, which makes save() merge()
        // an update against a nonexistent row instead of persist()-ing a new one,
        // throwing ObjectOptimisticLockingFailureException ("row already updated/deleted").
        author.setId(null);
        AuthorEntity savedAuthor = authorService.saveAuthor(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String dtoJson = objectMapper.writeValueAsString(authorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesAuthor() throws Exception
    {
        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // id must be nulled: TestDataUtil hardcodes id=1, which makes save() merge()
        // an update against a nonexistent row instead of persist()-ing a new one,
        // throwing ObjectOptimisticLockingFailureException ("row already updated/deleted").
        author.setId(null);
        AuthorEntity savedAuthor = authorService.saveAuthor(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoB();
        authorDto.setId(savedAuthor.getId());
        String dtoJson = objectMapper.writeValueAsString(authorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
        );
    }
}
