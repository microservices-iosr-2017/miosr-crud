package pl.edu.agh.iosr.microservices.crud.model;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteEntityMapper {

    @Autowired
    KeyFactory keyFactory;

    public FullEntity convert(Note note) {
        if (note.getId() == null)
            return Entity.newBuilder(keyFactory.newKey())
                    .set("author", note.getAuthor())
                    .set("title", note.getTitle())
                    .set("text", note.getText())
                    .build();
        else
            return Entity.newBuilder(keyFactory.newKey(note.getId()))
                    .set("author", note.getAuthor())
                    .set("title", note.getTitle())
                    .set("text", note.getText())
                    .build();
    }

    public Note convert(Entity entity) {
        if (entity == null)
            return null;

        Note note = new Note(
                entity.getString("author"),
                entity.getString("title"),
                entity.getString("text")
        );
        note.setId(entity.getKey().getId());
        return note;
    }
}
