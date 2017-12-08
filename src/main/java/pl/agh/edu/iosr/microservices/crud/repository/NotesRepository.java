package pl.agh.edu.iosr.microservices.crud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.agh.edu.iosr.microservices.crud.model.Note;

@RepositoryRestResource(collectionResourceRel = "notes", path = "notes")
public interface NotesRepository extends MongoRepository<Note, String> {
}

// curl -i -X POST -H "Content-Type:application/json" -d '{"author":"Aut","title":"Tit"}' localhost:8080/notes
