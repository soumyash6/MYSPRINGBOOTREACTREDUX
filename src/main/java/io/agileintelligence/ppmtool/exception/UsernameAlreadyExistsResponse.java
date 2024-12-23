package io.agileintelligence.ppmtool.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsernameAlreadyExistsResponse {

    String userExists;

    public UsernameAlreadyExistsResponse(String userExists) {
        this.userExists = userExists;
    }
}