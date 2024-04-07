package it.academy.DTO.response;

import it.academy.models.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {
    private List<OrderDTO> orderList;
    private Long count;
}
