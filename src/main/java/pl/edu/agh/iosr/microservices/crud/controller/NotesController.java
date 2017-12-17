package pl.edu.agh.iosr.microservices.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.iosr.microservices.crud.exception.NoteNotFoundException;
import pl.edu.agh.iosr.microservices.crud.exception.UserAuthorizationException;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import pl.edu.agh.iosr.microservices.crud.service.NotesService;
import pl.edu.agh.iosr.microservices.crud.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("/notes")
public class NotesController {

    private final Validator validator;

    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService, Validator validator) {
        this.notesService = notesService;
        this.validator = validator;
    }

    @RequestMapping(method = GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Note> getNotes() {
        return notesService.getNotes();
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createNote(@RequestBody Note note, HttpServletRequest request, HttpServletResponse response) throws UserAuthorizationException {
        if (!validator.validateHttpRequest(request))
            throw new UserAuthorizationException();

        notesService.saveNote(note);
        response.setHeader("Location", request.getRequestURL().append("/").append(note.getId()).toString());
    }

    @RequestMapping(method = GET, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Note getNoteById(@PathVariable(name = "id") String id) throws NoteNotFoundException {
        Note note = notesService.getNote(id);

        if (note == null)
            throw new NoteNotFoundException();

        return note;
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoteById(@PathVariable(name = "id") String id) {
        notesService.removeNote(id);
    }
}