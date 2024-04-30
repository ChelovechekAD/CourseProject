package it.academy.exceptions;

import it.academy.utilities.Constants;

public class ProductUsedInOrdersException extends RuntimeException{
    public ProductUsedInOrdersException(){
        super(Constants.THIS_PRODUCT_ALREADY_USED_IN_ORDER_S);
    }

}
