package it.academy.exceptions;

import it.academy.utilities.Constants;

public class OrderItemNotFoundException extends NotFoundException {

    public OrderItemNotFoundException(){
        super(Constants.ORDER_ITEM_NOT_FOUND);
    }

}
