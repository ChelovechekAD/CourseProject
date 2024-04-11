package it.academy.services.impl;

import it.academy.DAO.OrderDAO;
import it.academy.DAO.OrderItemDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.OrderDAOImpl;
import it.academy.DAO.impl.OrderItemDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.CreateOrderDTO;
import it.academy.DTO.request.OrderItemDTO;
import it.academy.DTO.response.OrderDTO;
import it.academy.DTO.response.OrderItemProductDTO;
import it.academy.DTO.response.OrderItemsDTO;
import it.academy.DTO.response.OrdersDTO;
import it.academy.enums.OrderStatus;
import it.academy.exceptions.*;
import it.academy.models.Order;
import it.academy.models.OrderItem;
import it.academy.models.Product;
import it.academy.models.User;
import it.academy.models.embedded.OrderItemPK;
import it.academy.services.OrderService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final TransactionHelper transactionHelper;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public OrderServiceImpl(){
        transactionHelper = new TransactionHelper();
        orderDAO = new OrderDAOImpl(transactionHelper);
        orderItemDAO = new OrderItemDAOImpl(transactionHelper);
        userDAO = new UserDAOImpl(transactionHelper);
        productDAO = new ProductDAOImpl(transactionHelper);
    }

    public void createOrder(@NonNull CreateOrderDTO createOrderDTO) {
        Runnable orderSupplier = () -> {
            User user = userDAO.get(createOrderDTO.getUserId());
            if (user == null){
                throw new UserNotFoundException();
            }
            Order order = Order.builder()
                    .userId(user)
                    .build();
            orderDAO.create(order);
            List<OrderItem> orderItemList = createOrderDTO.getOrderItemDTOList().stream()
                    .map(orderItemDTO -> {
                        Product product = productDAO.get(orderItemDTO.getProductId());
                        if (product == null){
                            throw new ProductNotFoundException();
                        }
                        OrderItem orderItem = Converter.convertOrderItemDTOToEntity(orderItemDTO);
                        orderItem.setOrderItemPK(
                                OrderItemPK.builder()
                                        .productId(product)
                                        .orderId(order)
                                        .build()
                        );
                        return orderItem;
                    }).collect(Collectors.toList());
            orderItemList.forEach(orderItemDAO::create);
        };
        transactionHelper.transaction(orderSupplier);
    }

    public void changeOrderStatus(@NonNull Long orderId, @NonNull OrderStatus orderStatus) {
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderId);
            if (order == null) {
                throw new OrderNotFoundException();
            }
            order.setOrderStatus(orderStatus);
        };
        transactionHelper.transaction(supplier);
    }

    public void deleteOrder(@NonNull Long orderId) {
        transactionHelper.transaction(() -> orderDAO.delete(orderId));
    }

    public OrdersDTO getListOfOrders(@NonNull Integer countPerPage, @NonNull Integer pageNum) {

        Supplier<OrdersDTO> supplier = () -> {
            List<Order> orderList = orderDAO.getPage(countPerPage, pageNum);
            List<OrderDTO> orderDTOList = orderList.stream().map(e->
                            Converter.convertOrderEntityToDTO(e, orderItemDAO.getCountOfByOrderId(e.getId())))
                    .collect(Collectors.toList());
            Long count = orderDAO.getCountOf();
            return new OrdersDTO(orderDTOList, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public OrderItemsDTO getOrderItems(@NonNull Long orderId, @NonNull Integer pageNum, @NonNull Integer countPerPage) {
        Supplier<OrderItemsDTO> supplier = () -> {
            if (orderDAO.get(orderId) == null){
                throw new OrderNotFoundException();
            }

            List<OrderItem> orderItemList = orderItemDAO.getOrderItemsPageByOrderId(orderId, pageNum, countPerPage);
            Long count = orderItemDAO.getCountOfByOrderId(orderId);
            List<OrderItemProductDTO> outListDTO = orderItemList.stream()
                    .map(i-> Converter.convertOrderItemAndProductEntitiesToDTO(i, i.getOrderItemPK().getProductId()))
                    .collect(Collectors.toList());
            return new OrderItemsDTO(outListDTO, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public void deleteOrderItem(@NonNull Long productId, @NonNull Long orderId){
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderId);
            Product product = productDAO.get(productId);
            validateOnExist(order, product);
            try{
                orderItemDAO.delete(new OrderItemPK(order, product));
            }catch (NotFoundException e){
                throw new OrderItemNotFoundException();
            }
        };
        transactionHelper.transaction(supplier);
    }

    public void updateOrderItem(@NonNull OrderItemDTO orderItemDTO){
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderItemDTO.getOrderId());
            Product product = productDAO.get(orderItemDTO.getProductId());
            validateOnExist(order, product);
            OrderItemPK pk = new OrderItemPK(order, product);
            if (orderItemDAO.get(pk) == null){
                throw new OrderItemNotFoundException();
            }
            OrderItem orderItem = Converter.convertOrderItemDTOToEntity(orderItemDTO);
            orderItem.setOrderItemPK(new OrderItemPK(order, product));
            orderItemDAO.update(orderItem);
        };
        transactionHelper.transaction(supplier);
    }
    public void addOrderItemToOrder(@NonNull OrderItemDTO orderItemDTO){
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderItemDTO.getOrderId());
            Product product = productDAO.get(orderItemDTO.getProductId());
            validateOnExist(order, product);
            OrderItemPK pk = new OrderItemPK(order, product);
            if (orderItemDAO.get(pk) != null){
                throw new OrderItemExistException();
            }
            OrderItem orderItem = Converter.convertOrderItemDTOToEntity(orderItemDTO);
            orderItem.setOrderItemPK(new OrderItemPK(order, product));
            orderItemDAO.create(orderItem);
        };
        transactionHelper.transaction(supplier);
    }
    private void validateOnExist(Order order, Product product){
        if (order == null) {
            throw new OrderNotFoundException();
        }
        if (product == null){
            throw new ProductNotFoundException();
        }
    }
}
