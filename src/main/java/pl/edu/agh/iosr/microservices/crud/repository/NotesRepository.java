package pl.edu.agh.iosr.microservices.crud.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.iosr.microservices.crud.model.Note;

import java.util.List;


@Component
public class NotesRepository {

    private DynamoDBMapper mapper;

    @Autowired
    public NotesRepository(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public void save(Note item) {
        mapper.save(item);
    }

    public Note findByNoteId(String noteId) {
        return mapper.load(Note.class, noteId);
    }

    public List<Note> findAll() {
        return mapper.scan(Note.class, new DynamoDBScanExpression());
    }

    public void delete(Note item) {
        mapper.delete(item);
    }

}
