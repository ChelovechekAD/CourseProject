package it.academy.models;

import it.academy.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "discounts",
        uniqueConstraints = @UniqueConstraint(columnNames = "discount_name")
)
public class Discount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "discount_name")
    private String discountName;
    @Column(nullable = false, name = "discount_value")
    private Double discountValue;
    @Column(nullable = false, name = "discount_type")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    @Column(name = "minimum_order_amount")
    private Double minimumOrderAmount;
    @Column(nullable = false, name = "start_at")
    private Date startAt;
    @Column(nullable = false, name = "end_at")
    private Date endAt;
    @Column(nullable = false, name = "is_active")
    @Builder.Default
    private Boolean isActive = Boolean.TRUE;

}
