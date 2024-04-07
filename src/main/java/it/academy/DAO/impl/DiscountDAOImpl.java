package it.academy.DAO.impl;

import it.academy.DAO.DiscountDAO;
import it.academy.models.Discount;

public class DiscountDAOImpl extends DAOImpl<Discount, Long> implements DiscountDAO {

    public DiscountDAOImpl(){
        super(Discount.class);
    }

}
