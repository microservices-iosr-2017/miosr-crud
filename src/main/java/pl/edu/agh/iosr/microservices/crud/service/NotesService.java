package pl.edu.agh.iosr.microservices.crud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import pl.edu.agh.iosr.microservices.crud.repository.NotesRepository;

import java.util.List;

@Service
public class NotesService {

    public final NotesRepository notesRepository;

    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public void saveNote(Note note) {
        notesRepository.save(note);
    }

    public Note getNote(String noteId) {
        return notesRepository.findByNoteId(noteId);
    }

    public List<Note> getNotes() {
        return notesRepository.findAll();
    }

    public Note removeNote(String noteId) {
        Note note = notesRepository.findByNoteId(noteId);
        notesRepository.delete(note);
        return note;
    }
}
