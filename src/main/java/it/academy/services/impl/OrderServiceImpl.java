package it.academy.services.impl;

import it.academy.DAO.*;
import it.academy.DAO.impl.*;
import it.academy.DTO.request.*;
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
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final TransactionHelper transactionHelper;
    private final OrderDAO orderDAO;
    private final OrderItemDAO orderItemDAO;
    private final CartItemDAO cartItemDAO;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public OrderServiceImpl() {
        transactionHelper = new TransactionHelper();
        orderDAO = new OrderDAOImpl(transactionHelper);
        orderItemDAO = new OrderItemDAOImpl(transactionHelper);
        userDAO = new UserDAOImpl(transactionHelper);
        productDAO = new ProductDAOImpl(transactionHelper);
        cartItemDAO = new CartItemDAOImpl(transactionHelper);
    }

    public void createOrder(@NonNull CreateOrderDTO createOrderDTO) {
        Runnable orderSupplier = () -> {
            User user = userDAO.get(createOrderDTO.getUserId());
            if (user == null) {
                throw new UserNotFoundException();
            }
            Order order = Order.builder()
                    .userId(user)
                    .build();
            orderDAO.create(order);
            List<OrderItem> orderItemList = createOrderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> {
                        Product product = productDAO.get(orderItemDTO.getProductId());
                        if (product == null) {
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
            cartItemDAO.deleteAllByUserId(user.getId());
            orderItemList.forEach(orderItemDAO::create);
        };
        transactionHelper.transaction(orderSupplier);
    }

    public void changeOrderStatus(@NonNull UpdateOrderStatusDTO dto) {
        Runnable supplier = () -> {
            OrderStatus status = dto.getOrderStatus();
            Order order = orderDAO.get(dto.getOrderId());
            if (order == null) {
                throw new OrderNotFoundException();
            }
            order.setOrderStatus(status);
        };
        transactionHelper.transaction(supplier);
    }

    public void deleteOrder(@NonNull Long orderId) {
        transactionHelper.transaction(() -> orderDAO.delete(orderId));
    }

    public OrdersDTO getListOfOrders(@NonNull RequestDataDetailsDTO dto) {

        Supplier<OrdersDTO> supplier = () -> {
            List<Order> orderList = orderDAO.getPage(dto.getCountPerPage(), dto.getPageNum());
            List<OrderDTO> orderDTOList = orderList.stream().map(e ->
                            Converter.convertOrderEntityToDTO(e, orderItemDAO.getCountOfByOrderId(e.getId())))
                    .collect(Collectors.toList());
            Long count = orderDAO.getCountOf();
            return new OrdersDTO(orderDTOList, count);
        };
        return transactionHelper.transaction(supplier);
    }
    public OrdersDTO getListOfUserOrders(@NonNull GetUserOrderPageDTO dto) {
        Supplier<OrdersDTO> supplier = () -> {

            List<Order> orderList = orderDAO.getUserOrders(dto.getPageNum(), dto.getCountPerPage(), dto.getUserId());
            List<OrderDTO> orderDTOList = orderList.stream().map(e ->
                            Converter.convertOrderEntityToDTO(e, orderItemDAO.getCountOfByOrderId(e.getId())))
                    .collect(Collectors.toList());
            Long count = orderDAO.countOfUserOrders(dto.getUserId());
            return new OrdersDTO(orderDTOList, count);
        };

        if (userDAO.get(dto.getUserId()) == null) {
            throw new UserNotFoundException();
        }
        return transactionHelper.transaction(supplier);
    }

    public OrderItemsDTO getOrderItems(@NonNull GetOrderItemsDTO dto) {
        Supplier<OrderItemsDTO> supplier = () -> {
            if (orderDAO.get(dto.getOrderId()) == null) {
                throw new OrderNotFoundException();
            }

            List<OrderItem> orderItemList = orderItemDAO
                    .getOrderItemsPageByOrderId(dto.getOrderId(), dto.getPageNum(), dto.getCountPerPage());
            Long count = orderItemDAO.getCountOfByOrderId(dto.getOrderId());
            List<OrderItemProductDTO> outListDTO = orderItemList.stream()
                    .map(i -> Converter.convertOrderItemAndProductEntitiesToDTO(i, i.getOrderItemPK().getProductId()))
                    .collect(Collectors.toList());
            return new OrderItemsDTO(outListDTO, count);
        };
        return transactionHelper.transaction(supplier);
    }

    public void deleteOrderItem(@NonNull DeleteOrderItemDTO dto) {
        Runnable supplier = () -> {
            Order order = orderDAO.get(dto.getOrderId());
            Product product = productDAO.get(dto.getProductId());
            validateOnExist(order, product);
            try {
                orderItemDAO.delete(new OrderItemPK(order, product));
            } catch (NotFoundException e) {
                throw new OrderItemNotFoundException();
            }
        };
        transactionHelper.transaction(supplier);
    }

    public void updateOrderItem(@NonNull OrderItemDTO orderItemDTO) {
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderItemDTO.getOrderId());
            Product product = productDAO.get(orderItemDTO.getProductId());
            validateOnExist(order, product);
            OrderItemPK pk = new OrderItemPK(order, product);
            if (orderItemDAO.get(pk) == null) {
                throw new OrderItemNotFoundException();
            }
            OrderItem orderItem = Converter.convertOrderItemDTOToEntity(orderItemDTO);
            orderItem.setOrderItemPK(new OrderItemPK(order, product));
            orderItemDAO.update(orderItem);
        };
        transactionHelper.transaction(supplier);
    }

    public void addOrderItemToOrder(@NonNull OrderItemDTO orderItemDTO) {
        Runnable supplier = () -> {
            Order order = orderDAO.get(orderItemDTO.getOrderId());
            Product product = productDAO.get(orderItemDTO.getProductId());
            validateOnExist(order, product);
            OrderItemPK pk = new OrderItemPK(order, product);
            if (orderItemDAO.get(pk) != null) {
                throw new OrderItemExistException();
            }
            OrderItem orderItem = Converter.convertOrderItemDTOToEntity(orderItemDTO);
            orderItem.setOrderItemPK(new OrderItemPK(order, product));
            orderItemDAO.create(orderItem);
        };
        transactionHelper.transaction(supplier);
    }

    private void validateOnExist(Order order, Product product) {
        if (order == null) {
            throw new OrderNotFoundException();
        }
        if (product == null) {
            throw new ProductNotFoundException();
        }
    }
}
