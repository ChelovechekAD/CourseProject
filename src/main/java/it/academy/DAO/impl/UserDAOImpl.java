package it.academy.DAO.impl;

import it.academy.DAO.UserDAO;
import it.academy.exceptions.UserNotFoundException;
import it.academy.models.Order;
import it.academy.models.*;
import it.academy.models.embedded.CartItemPK_;
import it.academy.models.embedded.OrderItemPK_;
import it.academy.models.embedded.ReviewPK_;
import it.academy.utilities.Constants;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.*;

public class UserDAOImpl extends DAOImpl<User, Long> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    public UserDAOImpl(TransactionHelper transactionHelper) {
        super(User.class, transactionHelper);
    }

    @Override
    public User getUserByEmail(String email) {
        String param = Constants.SELECT_FROM_USER_WHERE_EMAIL_USER.substring(
                Constants.SELECT_FROM_USER_WHERE_EMAIL_USER.lastIndexOf(Constants.SQL_INSERT_PARAM_SIGN) + 1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_FROM_USER_WHERE_EMAIL_USER, User.class)
                .setParameter(param, email)
                .getSingleResult();
    }

    @Override
    public Boolean existUserByEmail(String email) {
        String param = Constants.SELECT_COUNT_FROM_USER_WHERE_EMAIL_USER.substring(
                Constants.SELECT_COUNT_FROM_USER_WHERE_EMAIL_USER.lastIndexOf(Constants.SQL_INSERT_PARAM_SIGN) + 1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_COUNT_FROM_USER_WHERE_EMAIL_USER, Long.class)
                .setParameter(param, email)
                .getSingleResult() != 0;
    }

    @Override
    public boolean delete(Long id) {
        User user = get(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();

        CriteriaDelete<OrderItem> orderItemCriteriaDelete = cb.createCriteriaDelete(OrderItem.class);

        Subquery<Order> orderSubquery = orderItemCriteriaDelete.subquery(Order.class);
        Root<Order> subRoot = orderSubquery.from(Order.class);
        Join<Order, User> userJoin = subRoot.join(Order_.userId);
        orderSubquery.select(subRoot);
        orderSubquery.where(cb.equal(userJoin.get(User_.ID), id));

        CriteriaDelete<Order> orderCriteriaDelete = cb.createCriteriaDelete(Order.class);
        Root<Order> root = orderCriteriaDelete.from(Order.class);
        orderCriteriaDelete.where(cb.equal(root.get(Order_.USER_ID), user));
        transactionHelper.entityManager().createQuery(orderCriteriaDelete).executeUpdate();

        CriteriaDelete<CartItem> cartDeleteQuery = cb.createCriteriaDelete(CartItem.class);
        Root<CartItem> root1 = cartDeleteQuery.from(CartItem.class);
        cartDeleteQuery.where(cb.equal(root1.get(CartItem_.CART_ITEM_PK).get(CartItemPK_.USER_ID), user));
        transactionHelper.entityManager().createQuery(cartDeleteQuery).executeUpdate();

        CriteriaDelete<Review> reviewDeleteQuery = cb.createCriteriaDelete(Review.class);
        Root<Review> root2 = reviewDeleteQuery.from(Review.class);
        reviewDeleteQuery.where(cb.equal(root2.get(Review_.REVIEW_PK).get(ReviewPK_.USER_ID), user));
        transactionHelper.entityManager().createQuery(reviewDeleteQuery).executeUpdate();

        transactionHelper.remove(user);

        return get(id) == null;

    }
}
