package pl.edu.agh.iosr.microservices.crud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import pl.edu.agh.iosr.microservices.crud.repository.NotesRepository;

import java.util.List;

@Service
public class NotesService {

    private final NotesRepository notesRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Note saveNote(Note note) {
        return notesRepository.save(note);
    }

    public Note getNote(Long noteId) {
        return notesRepository.findByNoteId(noteId);
    }

    public List<Note> getNotes() {
        return notesRepository.findAll();
    }

    public void removeNote(Long noteId) {
        notesRepository.delete(noteId);
    }
}
