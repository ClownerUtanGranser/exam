package se.casparsylwan.cugexam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceAlreadyExsistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExsistException (String message )
    {
        super(message);
    }
}
