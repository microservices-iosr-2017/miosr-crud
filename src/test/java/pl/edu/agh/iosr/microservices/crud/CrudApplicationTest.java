package pl.edu.agh.iosr.microservices.crud;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.agh.iosr.microservices.crud.config.LocalDynamoDbRule;
import pl.edu.agh.iosr.microservices.crud.model.Note;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrudApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CrudApplicationTest {

    @ClassRule
    public static final LocalDynamoDbRule dynamoDBProvider = new LocalDynamoDbRule();

    @Autowired
    private MockMvc mockMvc;

    private static final String EXPECTED_AUTHOR = "Test Author";
    private static final String EXPECTED_TITLE = "Test Title";
    private static final String EXPECTED_TEXT = "Test Text";


    @Before
    public void before() {
        dynamoDBProvider.createTable(Note.class);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(Note.class);
    }


    @Test
    public void shouldCreateNote() throws Exception {
        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"author\": \"%s\", \"title\":\"%s\", \"text\":\"%s\"}", EXPECTED_AUTHOR, EXPECTED_TITLE, EXPECTED_TEXT)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("notes/")));
    }

    @Test
    public void shouldRetrieveNote() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"author\": \"%s\", \"title\":\"%s\", \"text\":\"%s\"}", EXPECTED_AUTHOR, EXPECTED_TITLE, EXPECTED_TEXT)))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(EXPECTED_AUTHOR))
                .andExpect(jsonPath("$.title").value(EXPECTED_TITLE))
                .andExpect(jsonPath("$.text").value(EXPECTED_TEXT));
    }

    @Test
    public void shouldDeleteNote() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"author\": \"%s\", \"title\":\"%s\"}", EXPECTED_AUTHOR, EXPECTED_TITLE)))
                .andExpect(status().isCreated()).andReturn();

        String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(delete(location))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(location))
                .andExpect(status().isNotFound());
    }

}

