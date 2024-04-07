package it.academy.DAO.impl;

import it.academy.DAO.CartItemDAO;
import it.academy.DAO.UserDAO;
import it.academy.models.*;
import it.academy.models.embedded.CartItemPK;
import it.academy.models.embedded.CartItemPK_;
import it.academy.utilities.Constants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CartItemDAOImpl extends DAOImpl<CartItem, CartItemPK> implements CartItemDAO {

    public CartItemDAOImpl(){
        super(CartItem.class);
    }

    @Override
    public List<CartItem> getAllByUserId(Long userId) {

        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<CartItem> cq = cb.createQuery(CartItem.class);
        Root<CartItem> root = cq.from(CartItem.class);
        Join<CartItem, CartItemPK> pkJoin = root.join(CartItem_.CART_ITEM_PK);
        Join<CartItem, User> userJoin = pkJoin.join(CartItemPK_.USER_ID);

        cq.select(root)
                .where(cb.equal(userJoin.get(User_.ID), userId));

        return transactionHelper.entityManager().createQuery(cq).getResultList();

        /*String paramName = Constants.SELECT_FROM_CART_ITEM_WHERE_USER_ID
                .substring(Constants.SELECT_FROM_CART_ITEM_WHERE_USER_ID.lastIndexOf(":")+1);
        return transactionHelper.entityManager()
                .createQuery(Constants.SELECT_FROM_CART_ITEM_WHERE_USER_ID, CartItem.class)
                .setParameter(paramName, userId)
                .getResultList();*/
    }
}
