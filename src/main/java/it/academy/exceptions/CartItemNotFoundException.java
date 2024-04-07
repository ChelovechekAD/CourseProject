package it.academy.exceptions;

import it.academy.utilities.Constants;

public class CartItemNotFoundException extends NotFoundException {

    public CartItemNotFoundException(){
        super(Constants.CART_ITEM_NOT_FOUND);
    }

}
