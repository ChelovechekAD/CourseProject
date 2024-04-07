package it.academy.DAO.impl;

import it.academy.DAO.ReviewDAO;
import it.academy.models.*;
import it.academy.models.embedded.ReviewPK;
import it.academy.models.embedded.ReviewPK_;
import jakarta.persistence.criteria.*;

public class ReviewDAOImpl extends DAOImpl<Review, ReviewPK> implements ReviewDAO {

    public ReviewDAOImpl(){
        super(Review.class);
    }


}
