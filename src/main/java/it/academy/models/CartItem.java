package it.academy.models;

import it.academy.models.embedded.CartItemPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class CartItem implements Serializable {

    @EmbeddedId
    private CartItemPK cartItemPK;

    @Column(nullable = false, name = "quantity")
    private int quantity;


}
