package it.academy.DTO.response;

import it.academy.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private Date date;
    private OrderStatus orderStatus;
    private Long countOfItems;
    private OrderUserDTO orderUserDTO;

}
