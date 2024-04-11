package it.academy.services;

import it.academy.DTO.request.AddToCartDTO;
import it.academy.DTO.request.DeleteItemFromCartDTO;
import it.academy.DTO.request.UpdatedItemCartDTO;
import it.academy.DTO.response.CartItemsDTO;
import lombok.NonNull;

public interface CartService {

    void addItemToCart(@NonNull AddToCartDTO dto);

    void deleteItemFromCart(@NonNull DeleteItemFromCartDTO dto);

    void updateItemFromCart(@NonNull UpdatedItemCartDTO dto);

    CartItemsDTO getAllCartByUserId(@NonNull Long userId);
}
