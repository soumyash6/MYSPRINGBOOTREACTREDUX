package io.agileintelligence.ppmtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectException(ProjectIdException pe, WebRequest request) {
        ProjectIdResponseError projectIdResponseError = new ProjectIdResponseError(pe.getMessage());
        return new ResponseEntity<>(projectIdResponseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectException(ProjectNotFoundException pe, WebRequest request) {
        ProjectNotFoundExceptionResponse projectNotFound = new ProjectNotFoundExceptionResponse(pe.getMessage());
        return new ResponseEntity<>(projectNotFound, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameNotFoundException pe, WebRequest request) {
        UsernameAlreadyExistsResponse usernameAlreadyExistsResponse = new UsernameAlreadyExistsResponse(pe.getMessage());
        return new ResponseEntity<>(usernameAlreadyExistsResponse, HttpStatus.BAD_REQUEST);
    }
}