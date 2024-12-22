package org.example.TEST3.Bottle;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bottle {
    @Id
    private String Type;
    private Integer Quantity;
}
