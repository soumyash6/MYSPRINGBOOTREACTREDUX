/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author SOUMYA SAHOO
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectidException  extends RuntimeException{

    public ProjectidException(String message) {
        super(message);
    }
    
}
