/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author SOUMYA SAHOO
 */
@RestController
@ControllerAdvice
public class CustomresponceexceptionHandler  extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler
    public final ResponseEntity<Object>  handelProjectexception(ProjectidException pe,WebRequest request)
    {
        ProjectidResponceError projectidResponceError=new ProjectidResponceError(pe.getMessage());
        return new ResponseEntity (projectidResponceError,HttpStatus.BAD_REQUEST);
     }
    @ExceptionHandler
    public final ResponseEntity<Object>  handelProjectexception(ProjectNotFoundException pe,WebRequest request)
    {
        ProjectnotFoundExceptionResponse projectNotFound=new ProjectnotFoundExceptionResponse(pe.getMessage());
        return new ResponseEntity (projectNotFound,HttpStatus.BAD_REQUEST);
     }
    
    @ExceptionHandler
    public final ResponseEntity<Object>  handelUsernameAlreadyExits(UsernotfoundException pe,WebRequest request)
    {
    	UsernameAlreadyExitsResponse projectNotFound=new UsernameAlreadyExitsResponse(pe.getMessage());
        return new ResponseEntity (projectNotFound,HttpStatus.BAD_REQUEST);
     }
}
