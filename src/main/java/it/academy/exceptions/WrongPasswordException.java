package it.academy.exceptions;

import it.academy.utilities.Constants;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super(Constants.WRONG_PASSWORD);
    }

}
