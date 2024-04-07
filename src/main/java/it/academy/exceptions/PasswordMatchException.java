package it.academy.exceptions;

import it.academy.utilities.Constants;

public class PasswordMatchException extends RuntimeException{

    public PasswordMatchException(){
        super(Constants.PASSWORD_MATCH_EXCEPTION);
    }

}
