package it.academy.services;

import it.academy.DTO.request.CreateOrderDTO;
import it.academy.DTO.request.OrderItemDTO;
import it.academy.DTO.response.OrderItemsDTO;
import it.academy.DTO.response.OrdersDTO;
import it.academy.enums.OrderStatus;
import lombok.NonNull;

public interface OrderService {
    void createOrder(@NonNull CreateOrderDTO createOrderDTO);
    void changeOrderStatus(@NonNull Long orderId, @NonNull OrderStatus orderStatus);
    void deleteOrder(@NonNull Long orderId);
    OrdersDTO getListOfOrders(@NonNull Integer countPerPage, @NonNull Integer pageNum);
    OrderItemsDTO getOrderItems(@NonNull Long orderId, @NonNull Integer pageNum, @NonNull Integer countPerPage);
    void deleteOrderItem(@NonNull Long productId, @NonNull Long orderId);
    void updateOrderItem(@NonNull OrderItemDTO orderItemDTO);
    void addOrderItemToOrder(@NonNull OrderItemDTO orderItemDTO);

}
