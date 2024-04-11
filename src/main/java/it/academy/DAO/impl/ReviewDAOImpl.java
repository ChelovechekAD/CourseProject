package it.academy.DAO.impl;

import it.academy.DAO.ReviewDAO;
import it.academy.models.RefreshToken;
import it.academy.models.Review;
import it.academy.models.embedded.ReviewPK;
import it.academy.utilities.TransactionHelper;

public class ReviewDAOImpl extends DAOImpl<Review, ReviewPK> implements ReviewDAO {

    public ReviewDAOImpl() {
        super(Review.class);
    }

    public ReviewDAOImpl(TransactionHelper transactionHelper) {
        super(Review.class, transactionHelper);
    }


}
