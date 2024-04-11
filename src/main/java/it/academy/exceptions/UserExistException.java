package it.academy.exceptions;

import it.academy.utilities.Constants;

public class UserExistException extends RuntimeException {

    public UserExistException() {
        super(Constants.USER_ALREADY_EXIST);
    }

}
