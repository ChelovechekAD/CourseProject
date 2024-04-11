package it.academy.exceptions;

import it.academy.utilities.Constants;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super(Constants.ENTITY_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message);
    }
    /*public NotFoundException(String message, StackTraceElement[] stackTraceElements){
        super(message);
        super.setStackTrace(stackTraceElements);
    }*/

}
