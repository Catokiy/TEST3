package org.example.TEST3.waitingOrder;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="waitingOrder")
public class waitingOrder {
    private Integer Id;
    private String clientType;
    private String clientName;
    private String waterType;
    private String bottleType;
    private Integer bottleQuantity;
    private String orderDate;
    private String deliveryDate;
    private String deliveryAddress;

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return Id;
    }

}
