package it.academy.exceptions;

import it.academy.utilities.Constants;
import liquibase.util.grammar.Token;

public class TokenNotFound extends RuntimeException{

    public TokenNotFound(){
        super(Constants.TOKEN_NOT_FOUND);
    }

}
