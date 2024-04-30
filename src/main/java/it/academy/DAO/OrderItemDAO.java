package it.academy.DAO;

import it.academy.models.Order;
import it.academy.models.OrderItem;
import it.academy.models.embedded.OrderItemPK;

import java.util.List;

public interface OrderItemDAO extends DAO<OrderItem, OrderItemPK> {
    Long getCountOfByOrderId(Long orderId);

    List<OrderItem> getOrderItemsPageByOrderId(Long orderId, Integer pageNum, Integer countPerPage);

    boolean existByProductId(Long productId);


}
