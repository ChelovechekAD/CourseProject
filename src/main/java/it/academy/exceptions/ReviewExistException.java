package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ReviewExistException extends RuntimeException {

    public ReviewExistException() {
        super(Constants.REVIEW_ALREADY_EXIST);
    }

}
