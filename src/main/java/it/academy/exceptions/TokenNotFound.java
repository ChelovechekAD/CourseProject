package it.academy.exceptions;

import it.academy.utilities.Constants;

public class TokenNotFound extends NotFoundException {

    public TokenNotFound() {
        super(Constants.TOKEN_NOT_FOUND);
    }

}
