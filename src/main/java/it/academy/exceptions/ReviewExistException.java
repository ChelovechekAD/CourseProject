package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ReviewExistException extends ExistException {

    public ReviewExistException() {
        super(Constants.REVIEW_ALREADY_EXIST);
    }

}
