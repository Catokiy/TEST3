package org.example.TEST3.Order;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    private Integer Id;
    private String clientType;
    private String clientName;
    private String waterType;
    private String bottleType;
    private Integer bottleQuantity;
    private String orderDate;
    private String deliveryDate;
    private String deliveryAddress;
    private String managerInCharge;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return Id;
    }
}
