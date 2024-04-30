package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ExistException extends RuntimeException {

    public ExistException(String e) {
        super(e);
    }

    public ExistException() {
        super(Constants.EXIST_EXCEPTION_MESSAGE);
    }

}
