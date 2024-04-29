package it.academy.DAO.impl;

import it.academy.DAO.ReviewDAO;
import it.academy.models.*;
import it.academy.models.embedded.ReviewPK;
import it.academy.models.embedded.ReviewPK_;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class ReviewDAOImpl extends DAOImpl<Review, ReviewPK> implements ReviewDAO {

    public ReviewDAOImpl() {
        super(Review.class);
    }

    public ReviewDAOImpl(TransactionHelper transactionHelper) {
        super(Review.class, transactionHelper);
    }


    @Override
    public List<Review> getReviewsOnProduct(Integer pageNum, Integer countPerPage, Long productId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> root = cq.from(Review.class);
        Join<Review, ReviewPK> pkJoin = root.join(Review_.REVIEW_PK);
        Join<ReviewPK, Product_> productJoin = pkJoin.join(ReviewPK_.PRODUCT_ID);
        cq.select(root)
                .where(cb.equal(productJoin.get(Product_.ID), productId));
        return transactionHelper.entityManager().createQuery(cq)
                .setMaxResults(countPerPage)
                .setFirstResult(countPerPage * (pageNum - 1))
                .getResultList();
    }

    @Override
    public Long getCountOfReviewsOnProduct(Long productId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Review> root = cq.from(Review.class);
        Join<Review, ReviewPK> pkJoin = root.join(Review_.REVIEW_PK);
        Join<ReviewPK, Product> productJoin = pkJoin.join(ReviewPK_.PRODUCT_ID);
        cq.select(cb.count(root))
                .where(cb.equal(productJoin.get(Product_.ID), productId));
        return transactionHelper.entityManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Long getCountOfUserReviews(Long userId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Review> root = cq.from(Review.class);
        Join<Review, ReviewPK> pkJoin = root.join(Review_.REVIEW_PK);
        Join<ReviewPK, User> userJoin = pkJoin.join(ReviewPK_.USER_ID);
        cq.select(cb.count(root))
                .where(cb.equal(userJoin.get(User_.ID), userId));
        return transactionHelper.entityManager().createQuery(cq).getSingleResult();
    }
}
