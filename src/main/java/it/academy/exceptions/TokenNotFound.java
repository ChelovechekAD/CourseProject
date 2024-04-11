package it.academy.exceptions;

import it.academy.utilities.Constants;

public class TokenNotFound extends RuntimeException {

    public TokenNotFound() {
        super(Constants.TOKEN_NOT_FOUND);
    }

}
