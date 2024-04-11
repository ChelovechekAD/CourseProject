package it.academy.exceptions;

import it.academy.utilities.Constants;

public class CartItemExistException extends RuntimeException {

    public CartItemExistException() {
        super(Constants.CART_ITEM_ALREADY_EXIST);
    }

}
