package pl.edu.agh.iosr.microservices.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User autorization failed.")
public class UserAuthorizationException extends Exception {
}
