package it.academy.DAO;

import it.academy.models.Order;

import java.util.List;

public interface OrderDAO extends DAO<Order, Long> {

    List<Order> getUserOrders(Integer pageNum, Integer countPerPage, Long userId);
    Long countOfUserOrders(Long userId);

}
