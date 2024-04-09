package it.academy.exceptions;

import it.academy.utilities.Constants;

public class RefreshTokenInvalidException extends RuntimeException{

    public RefreshTokenInvalidException(){
        super(Constants.TOKEN_INVALID);
    }

}
