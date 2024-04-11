package it.academy.DAO.impl;

import it.academy.DAO.CartItemDAO;
import it.academy.models.CartItem;
import it.academy.models.CartItem_;
import it.academy.models.User;
import it.academy.models.User_;
import it.academy.models.embedded.CartItemPK;
import it.academy.models.embedded.CartItemPK_;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CartItemDAOImpl extends DAOImpl<CartItem, CartItemPK> implements CartItemDAO {

    public CartItemDAOImpl() {
        super(CartItem.class);
    }

    public CartItemDAOImpl(TransactionHelper transactionHelper) {
        super(CartItem.class, transactionHelper);
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

    }

    @Override
    public boolean deleteAllByUserId(Long userId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaDelete<CartItem> cartDeleteQuery = cb.createCriteriaDelete(CartItem.class);
        Root<CartItem> root1 = cartDeleteQuery.from(CartItem.class);
        cartDeleteQuery.where(cb.equal(root1.get(CartItem_.CART_ITEM_PK).get(CartItemPK_.USER_ID).get(User_.ID), userId));
        transactionHelper.entityManager().createQuery(cartDeleteQuery).executeUpdate();
        return true;
    }
}
