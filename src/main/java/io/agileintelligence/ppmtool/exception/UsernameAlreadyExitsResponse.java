package io.agileintelligence.ppmtool.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsernameAlreadyExitsResponse {

    String userexits;

    public UsernameAlreadyExitsResponse(String userexits) {
        this.userexits = userexits;
    }


}
