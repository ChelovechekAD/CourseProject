package it.academy.exceptions;

import it.academy.utilities.Constants;

public class RefreshTokenExistException extends RuntimeException{

    public RefreshTokenExistException(){
        super(Constants.REFRESH_TOKEN_ALREADY_EXIST);
    }

}
