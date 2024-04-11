package it.academy.DAO.impl;

import it.academy.DAO.OrderDAO;
import it.academy.exceptions.OrderNotFoundException;
import it.academy.models.*;
import it.academy.models.embedded.OrderItemPK;
import it.academy.models.embedded.OrderItemPK_;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class OrderDAOImpl extends DAOImpl<Order, Long> implements OrderDAO {

    public OrderDAOImpl() {
        super(Order.class);
    }
    public OrderDAOImpl(TransactionHelper transactionHelper){
        super(Order.class, transactionHelper);
    }

    @Override
    public boolean delete(Long id) {
        Order order = get(id);
        if (order == null) {
            throw new OrderNotFoundException();
        }

        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaDelete<OrderItem> cq = cb.createCriteriaDelete(OrderItem.class);
        Root<OrderItem> root = cq.from(OrderItem.class);
        Join<OrderItem, OrderItemPK> pkJoin = root.join(OrderItem_.ORDER_ITEM_PK);
        Join<OrderItemPK, Order> orderJoin = pkJoin.join(OrderItemPK_.ORDER_ID);

        cq.where(cb.equal(orderJoin.get(Order_.ID), id));

        transactionHelper.entityManager()
                .createQuery(cq)
                .executeUpdate();

        transactionHelper.remove(order);
        return get(id) == null;
    }
}
