package it.academy.DAO.impl;

import it.academy.DAO.OrderItemDAO;
import it.academy.models.*;
import it.academy.models.embedded.OrderItemPK;
import it.academy.models.embedded.OrderItemPK_;
import it.academy.utilities.TransactionHelper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class OrderItemDAOImpl extends DAOImpl<OrderItem, OrderItemPK> implements OrderItemDAO {

    public OrderItemDAOImpl() {
        super(OrderItem.class);
    }

    public OrderItemDAOImpl(TransactionHelper transactionHelper) {
        super(OrderItem.class, transactionHelper);
    }

    @Override
    public boolean existByProductId(Long productId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<OrderItem> root = cq.from(OrderItem.class);
        cq.select(cb.count(root))
                .where(cb.equal(root.get(OrderItem_.ORDER_ITEM_PK).get(OrderItemPK_.PRODUCT_ID).get(Product_.ID), productId));
        return transactionHelper.entityManager().createQuery(cq).getSingleResult() != 0;
    }

    @Override
    public List<OrderItem> getOrderItemsPageByOrderId(Long orderId, Integer pageNum, Integer countPerPage) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<OrderItem> cq = cb.createQuery(OrderItem.class);
        Root<OrderItem> root = cq.from(OrderItem.class);
        Join<OrderItem, OrderItemPK> pkJoin = root.join(OrderItem_.ORDER_ITEM_PK);
        Join<OrderItemPK, Order> orderJoin = pkJoin.join(OrderItemPK_.ORDER_ID);

        cq.select(root)
                .where(cb.equal(orderJoin.get(Order_.ID), orderId));

        return transactionHelper.entityManager()
                .createQuery(cq)
                .setFirstResult(countPerPage * (pageNum - 1))
                .setMaxResults(countPerPage)
                .getResultList();
    }

    @Override
    public Long getCountOfByOrderId(Long orderId) {
        CriteriaBuilder cb = transactionHelper.criteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<OrderItem> root = cq.from(OrderItem.class);
        Join<OrderItem, OrderItemPK> pkJoin = root.join(OrderItem_.ORDER_ITEM_PK);
        Join<OrderItemPK, Order> orderJoin = pkJoin.join(OrderItemPK_.ORDER_ID);

        cq.select(cb.count(root))
                .where(cb.equal(orderJoin.get(Order_.ID), orderId));

        return transactionHelper.entityManager()
                .createQuery(cq)
                .getSingleResult();
    }

}
