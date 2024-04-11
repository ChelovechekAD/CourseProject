package it.academy.exceptions;

import it.academy.utilities.Constants;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(Constants.USER_NOT_FOUND);
    }
}
