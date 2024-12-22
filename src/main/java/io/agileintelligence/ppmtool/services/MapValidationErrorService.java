/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author SOUMYA SAHOO
 */
@Service
public class MapValidationErrorService {

    public ResponseEntity<?> throughError(BindingResult result) {
        HashMap<String, String> errorMap = new LinkedHashMap<String, String>();
        if (result.hasErrors()) {
            for (FieldError errorInProject : result.getFieldErrors()) {
                errorMap.put(errorInProject.getField(), errorInProject.getDefaultMessage());
            }
            return new ResponseEntity<HashMap<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
            // return  new ResponseEntity<String>("some Error in GEtting project",HttpStatus.BAD_REQUEST);
            // return  new ResponseEntity<HashMap<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
