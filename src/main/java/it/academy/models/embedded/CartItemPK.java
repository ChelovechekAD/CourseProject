package it.academy.models.embedded;

import it.academy.models.Product;
import it.academy.models.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class CartItemPK implements Serializable {
    @JoinColumn(nullable = false, name = "user_id")
    @ManyToOne
    private User userId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    private Product productId;
}
