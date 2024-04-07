package it.academy.services.impl;

import it.academy.DAO.CartItemDAO;
import it.academy.DAO.ProductDAO;
import it.academy.DAO.UserDAO;
import it.academy.DAO.impl.CartItemDAOImpl;
import it.academy.DAO.impl.ProductDAOImpl;
import it.academy.DAO.impl.UserDAOImpl;
import it.academy.DTO.request.AddToCartDTO;
import it.academy.DTO.request.UpdatedItemCartDTO;
import it.academy.DTO.response.CartItemDTO;
import it.academy.DTO.response.CartItemsDTO;
import it.academy.exceptions.*;
import it.academy.models.CartItem;
import it.academy.models.Product;
import it.academy.models.User;
import it.academy.models.embedded.CartItemPK;
import it.academy.services.CartService;
import it.academy.utilities.Converter;
import it.academy.utilities.TransactionHelper;
import lombok.NonNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    public void addItemToCart(@NonNull AddToCartDTO dto){
        Runnable supplier = () -> {
            Product product = productDAO.get(dto.getProductId());
            User user = userDAO.get(dto.getUserId());
            validateCartItemPK(user, product);
            CartItemPK cartItemPK = new CartItemPK(user, product);
            if (cartItemDAO.get(cartItemPK) != null){
                throw new CartItemExistException();
            }

            CartItem cartItem = CartItem.builder()
                    .cartItemPK(cartItemPK)
                    .quantity(dto.getQuantity())
                    .build();

            cartItemDAO.create(cartItem);
        };
        transactionHelper.transaction(supplier);
    }

    public void deleteItemFromCart(@NonNull Long prodId, @NonNull Long userId){
        try{
            Runnable runnable = () -> {
                Product product = productDAO.get(prodId);
                User user = userDAO.get(userId);
                validateCartItemPK(user, product);
                CartItemPK cartItemPK = new CartItemPK(user, product);
                cartItemDAO.delete(cartItemPK);
            };
            transactionHelper.transaction(() -> runnable);
        }catch (NotFoundException e){
            throw new CartItemNotFoundException();
        }
    }

    public void updateItemFromCart(@NonNull UpdatedItemCartDTO dto){
        Runnable runnable = () -> {
            Product product = productDAO.get(dto.getProductId());
            User user = userDAO.get(dto.getUserId());
            validateCartItemPK(user, product);
            CartItemPK cartItemPK = new CartItemPK(user, product);
            CartItem cartItem = cartItemDAO.get(cartItemPK);
            if (cartItem == null) {
                throw new CartItemNotFoundException();
            }
            cartItem.setQuantity(dto.getQuantity());
            cartItemDAO.update(cartItem);
        };
        transactionHelper.transaction(runnable);
    }

    public CartItemsDTO getAllCartByUserId(@NonNull Long userId){
        Supplier<CartItemsDTO> supplier = () -> {
            User user = userDAO.get(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            List<CartItem> cartItems = cartItemDAO.getAllByUserId(userId);
            List<CartItemDTO> list = cartItems.stream()
                    .map(Converter::convertListCartItemEntityToListDTO)
                    .collect(Collectors.toList());
            return new CartItemsDTO(list);
        };
        return transactionHelper.transaction(supplier);
    }

    private void validateCartItemPK(User user, Product product){
        if (product == null) {
            throw new ProductNotFoundException();
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
    }
}
