package it.academy.DAO;

import it.academy.models.CartItem;
import it.academy.models.embedded.CartItemPK;

import java.util.List;

public interface CartItemDAO extends DAO<CartItem, CartItemPK> {
    List<CartItem> getAllByUserId(Long userId);

    boolean deleteAllByUserId(Long userId);
}
