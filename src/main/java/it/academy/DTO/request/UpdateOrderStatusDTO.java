package it.academy.DTO.request;

import it.academy.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusDTO {

    private Long orderId;
    private OrderStatus orderStatus;

}
