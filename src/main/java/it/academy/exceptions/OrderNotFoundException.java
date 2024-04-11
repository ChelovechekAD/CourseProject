package it.academy.exceptions;

import it.academy.utilities.Constants;

public class OrderNotFoundException extends NotFoundException {

    public OrderNotFoundException() {
        super(Constants.ORDER_NOT_FOUND);
    }
}
