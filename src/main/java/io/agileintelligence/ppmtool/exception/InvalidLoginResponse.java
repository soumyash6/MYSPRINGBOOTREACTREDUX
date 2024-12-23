package io.agileintelligence.ppmtool.exception;

public class InvalidLoginResponse {
    private String invalidUsername;
    private String invalidPassword;

    public InvalidLoginResponse() {
        this.invalidUsername = "Invalid Username";
        this.invalidPassword = "Invalid Password";
    }

    public String getInvalidUsername() {
        return invalidUsername;
    }

    public void setInvalidUsername(String invalidUsername) {
        this.invalidUsername = invalidUsername;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }
}