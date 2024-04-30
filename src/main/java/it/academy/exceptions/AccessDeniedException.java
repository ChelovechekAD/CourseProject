package it.academy.exceptions;

import it.academy.utilities.Constants;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(){
        super(Constants.ACCESS_DENIED);
    }

    public AccessDeniedException(String e){
        super(e);
    }

}
