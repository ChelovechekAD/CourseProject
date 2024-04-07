package it.academy.exceptions;

import it.academy.utilities.Constants;

public class CatalogNotFoundException extends NotFoundException {

    public CatalogNotFoundException(){
        super(Constants.REQUESTED_CATALOG_NOT_EXIST);
    }

}
