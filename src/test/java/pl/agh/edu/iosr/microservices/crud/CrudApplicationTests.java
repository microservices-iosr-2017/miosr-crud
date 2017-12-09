package pl.agh.edu.iosr.microservices.crud;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.agh.edu.iosr.microservices.crud.repository.NotesRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrudApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NotesRepository notesRepository;

	@Before
    @After
	public void deleteAllBeforeTests() throws Exception {
		notesRepository.deleteAll();
	}

	@Test
	public void shouldCreateNote() throws Exception {
		mockMvc.perform(post("/notes")
                .content("{\"author\": \"Test Author\", \"title\":\"Test Title\", \"text\":\"Test Text\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("notes/")));
	}

	@Test
	public void shouldRetrieveNote() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/notes")
                .content("{\"author\": \"Test Author\", \"title\":\"Test Title\", \"text\":\"Test Text\", \"date\":\"2017-01-01\"}"))
                .andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.text").value("Test Text"))
                .andExpect(jsonPath("$.date").value(containsString("2017-01-01")));
	}

	@Test
	public void shouldDeleteNote() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/notes")
                .content("{ \"author\": \"Test Author\", \"title\":\"Test Title\"}"))
                .andExpect(status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(delete(location))
                .andExpect(status().isNoContent());

		mockMvc.perform(get(location))
                .andExpect(status().isNotFound());
	}

}
