package io.agileintelligence.ppmtool.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectIdResponseError {

    String projectNotFound;

    public ProjectIdResponseError(String projectIdentifier) {
        this.projectNotFound = projectIdentifier;
    }
}