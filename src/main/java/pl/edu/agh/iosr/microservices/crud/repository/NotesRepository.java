package pl.edu.agh.iosr.microservices.crud.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import pl.edu.agh.iosr.microservices.crud.model.NoteEntityMapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class NotesRepository {

    @Value("${google.cloud.datastore.kind}")
    private String datastoreKind;

    @Autowired
    private Datastore datastore;

    @Autowired
    private KeyFactory keyFactory;

    @Autowired
    private NoteEntityMapper mapper;

    public Note save(Note note) {
        return mapper.convert(datastore.put(mapper.convert(note)));
    }

    public Note findByNoteId(Long noteId) {
        return mapper.convert(datastore.get(keyFactory.newKey(noteId)));
    }

    public List<Note> findAll() {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(datastoreKind).build();
        Iterator<Entity> results = datastore.run(query);
        List<Note> notes = new LinkedList<>();

        while (results.hasNext()) {
            notes.add(mapper.convert(results.next()));
        }

        return notes;
    }

    public void delete(Long noteId) {
        datastore.delete(keyFactory.newKey(noteId));
    }

}
