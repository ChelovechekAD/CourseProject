package it.academy.exceptions;

import it.academy.utilities.Constants;

public class OrderItemExistException extends RuntimeException {

    public OrderItemExistException() {
        super(Constants.ORDER_ITEM_EXIST);
    }

}
