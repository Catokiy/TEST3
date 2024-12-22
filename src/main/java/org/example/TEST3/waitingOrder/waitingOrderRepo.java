package org.example.TEST3.waitingOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface waitingOrderRepo extends JpaRepository<waitingOrder, Integer> {
    @Override
    @Query("select t from waitingOrder t order by t.id")
    List<waitingOrder> findAll();
    @Query("select t from waitingOrder t where CONCAT(t.id, t.clientType, t.clientName, t.waterType, t.bottleType, t.bottleQuantity, t.orderDate, t.deliveryDate, t.deliveryAddress) like %?1% order by t.id")
    List<waitingOrder> search(String keyword);

    @Query("select t from waitingOrder t where t.deliveryDate like %?1% order by t.id")
    List<waitingOrder> searchFiltered(String date);
}
