package it.academy.exceptions;

import it.academy.utilities.Constants;

public class OrderItemExistException extends ExistException {

    public OrderItemExistException() {
        super(Constants.ORDER_ITEM_EXIST);
    }

}
