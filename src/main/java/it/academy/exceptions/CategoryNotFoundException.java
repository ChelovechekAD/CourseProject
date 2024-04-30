package it.academy.exceptions;

import it.academy.utilities.Constants;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException() {
        super(Constants.REQUESTED_CATALOG_NOT_EXIST);
    }

}
