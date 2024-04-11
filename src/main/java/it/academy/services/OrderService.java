package it.academy.services;

import it.academy.DTO.request.*;
import it.academy.DTO.response.OrderItemsDTO;
import it.academy.DTO.response.OrdersDTO;
import lombok.NonNull;

public interface OrderService {
    void createOrder(@NonNull CreateOrderDTO createOrderDTO);

    void changeOrderStatus(@NonNull UpdateOrderStatusDTO dto);

    void deleteOrder(@NonNull Long orderId);

    OrdersDTO getListOfOrders(@NonNull RequestDataDetailsDTO dto);

    OrderItemsDTO getOrderItems(@NonNull GetOrderItemsDTO dto);

    void deleteOrderItem(@NonNull DeleteOrderItemDTO dto);

    void updateOrderItem(@NonNull OrderItemDTO orderItemDTO);

    void addOrderItemToOrder(@NonNull OrderItemDTO orderItemDTO);

}
