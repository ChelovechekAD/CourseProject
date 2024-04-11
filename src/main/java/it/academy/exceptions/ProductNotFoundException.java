package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException() {
        super(Constants.PRODUCT_NOT_FOUND);
    }
}
