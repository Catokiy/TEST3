package org.example.TEST3.Order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    @Override
    @Query("select t from Order t order by t.id")
    List<Order> findAll();
    @Query("select t from Order t where CONCAT(t.id, t.clientType, t.clientName, t.waterType, t.bottleType, t.bottleQuantity, t.orderDate, t.deliveryDate, t.deliveryAddress) like %?1% order by t.id")
    List<Order> search(String keyword);

    @Query("select t from Order t where t.deliveryDate like %?1% order by t.id")
    List<Order> searchFiltered(String date);
}
