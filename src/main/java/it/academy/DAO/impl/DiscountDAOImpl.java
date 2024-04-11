package it.academy.DAO.impl;

import it.academy.DAO.DiscountDAO;
import it.academy.models.Category;
import it.academy.models.Discount;
import it.academy.utilities.TransactionHelper;

public class DiscountDAOImpl extends DAOImpl<Discount, Long> implements DiscountDAO {

    public DiscountDAOImpl(){
        super(Discount.class);
    }
    public DiscountDAOImpl(TransactionHelper transactionHelper){
        super(Discount.class, transactionHelper);
    }

}
