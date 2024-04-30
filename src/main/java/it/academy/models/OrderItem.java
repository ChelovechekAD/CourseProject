package it.academy.models;

import it.academy.models.embedded.OrderItemPK;
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
@Table(name = "order_item")
public class OrderItem implements Serializable {

    @EmbeddedId
    private OrderItemPK orderItemPK;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private Double price;
}
