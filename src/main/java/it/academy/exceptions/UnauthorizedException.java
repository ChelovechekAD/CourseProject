package it.academy.exceptions;

import it.academy.utilities.Constants;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super(Constants.UNAUTHORIZED);
    }

    public UnauthorizedException(String e) {
        super(e);
    }

}
