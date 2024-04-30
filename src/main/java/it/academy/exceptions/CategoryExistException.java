package it.academy.exceptions;

import it.academy.utilities.Constants;

public class CategoryExistException extends ExistException {

    public CategoryExistException() {
        super(Constants.CATEGORY_ALREADY_EXIST_EXCEPTION_MESSAGE);
    }

}
