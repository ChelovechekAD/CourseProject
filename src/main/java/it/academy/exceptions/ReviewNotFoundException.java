package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ReviewNotFoundException extends NotFoundException{

    public ReviewNotFoundException(){
        super(Constants.REVIEW_NOT_FOUND);
    }
}
