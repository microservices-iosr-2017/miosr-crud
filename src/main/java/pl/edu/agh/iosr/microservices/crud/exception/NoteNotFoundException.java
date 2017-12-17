package pl.edu.agh.iosr.microservices.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No note with specified attributes found.")
public class NoteNotFoundException extends Exception {
}
