package pl.edu.agh.iosr.microservices.crud;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.iosr.microservices.crud.config.LocalDynamoDbRule;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.iosr.microservices.crud.repository.NotesRepository;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrudApplication.class)
@ActiveProfiles("test")
public class NoteRepositoryTest {

    @Autowired
    NotesRepository notesRepository;

    @ClassRule
    public static final LocalDynamoDbRule dynamoDBProvider = new LocalDynamoDbRule();

    private static final String EXPECTED_AUTHOR = "Test Author";
    private static final String EXPECTED_TITLE = "Test Title";
    private static final String EXPECTED_TEXT = "Test Text";
    private static final String EXPECTED_AUTHOR2 = "Test Author2";
    private static final String EXPECTED_TITLE2 = "Test Title2";
    private static final String EXPECTED_TEXT2 = "Test Text2";


    @Before
    public void before() {
        dynamoDBProvider.createTable(Note.class);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(Note.class);
    }


    @Test
    public void shouldInsertNoteToDatabase() {
        Note note = new Note(EXPECTED_AUTHOR, EXPECTED_TITLE, EXPECTED_TEXT);
        notesRepository.save(note);

        Note result = notesRepository.findByNoteId(note.getId());
        assertTrue("Contains note with expected author", result.getAuthor().equals(EXPECTED_AUTHOR));
        assertTrue("Contains note with expected author", result.getTitle().equals(EXPECTED_TITLE));
        assertTrue("Contains note with expected author", result.getText().equals(EXPECTED_TEXT));
    }

    @Test
    public void shouldExtractNotesFromDatabase() {
        int initialSize = notesRepository.findAll().size();

        Note note1 = new Note(EXPECTED_AUTHOR, EXPECTED_TITLE, EXPECTED_TEXT);
        Note note2 = new Note(EXPECTED_AUTHOR2, EXPECTED_TITLE2, EXPECTED_TEXT2);
        notesRepository.save(note1);
        notesRepository.save(note2);

        List<Note> result = notesRepository.findAll();
        assertTrue("Not empty", result.size() == initialSize + 2);
        assertTrue("Contains note1", containsNote(result, note1.getId()));
        assertTrue("Contains note2", containsNote(result, note2.getId()));
    }

    @Test
    public void shouldRemoveNoteFromDatabase() {
        int initialSize = notesRepository.findAll().size();

        Note note1 = new Note(EXPECTED_AUTHOR, EXPECTED_TITLE, EXPECTED_TEXT);
        Note note2 = new Note(EXPECTED_AUTHOR2, EXPECTED_TITLE2, EXPECTED_TEXT2);
        notesRepository.save(note1);
        notesRepository.save(note2);

        List<Note> result = notesRepository.findAll();
        assertTrue("Not empty", result.size() == initialSize + 2);
        assertTrue("Contains note1", containsNote(result, note1.getId()));
        assertTrue("Contains note2", containsNote(result, note2.getId()));

        notesRepository.delete(note1);

        result = notesRepository.findAll();
        assertTrue("Not empty", result.size() == initialSize + 1);
        assertFalse("Does not contain note1", containsNote(result, note1.getId()));
        assertTrue("Contains note2", containsNote(result, note2.getId()));
    }

    private boolean containsNote(List<Note> list, String noteId) {
        return list.stream().anyMatch(note -> note.getId().equals(noteId));
    }
}
