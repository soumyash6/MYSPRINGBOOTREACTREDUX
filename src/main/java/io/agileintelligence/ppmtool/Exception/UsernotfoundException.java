package io.agileintelligence.ppmtool.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernotfoundException extends RuntimeException{
	
	 public UsernotfoundException(String message) {
	        super(message);
	    }

}
