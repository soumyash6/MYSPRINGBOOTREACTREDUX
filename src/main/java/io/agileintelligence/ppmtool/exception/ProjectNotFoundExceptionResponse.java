/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.agileintelligence.ppmtool.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SOUMYA SAHOO
 */
@Setter
@Getter
public class ProjectNotFoundExceptionResponse {

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        this.projectNotFound = projectNotFound;
    }

    String projectNotFound;
}
