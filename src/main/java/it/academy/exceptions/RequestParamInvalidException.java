package it.academy.exceptions;

import it.academy.utilities.Constants;

public class RequestParamInvalidException extends RuntimeException{

    public RequestParamInvalidException(){
        super(Constants.REQUIRED_REQUEST_PARAMETERS_DOESNT_EXIST_OR_INVALID);
    }
    public RequestParamInvalidException(String e){super(e);}
}
