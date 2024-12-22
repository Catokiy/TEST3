package org.example.TEST3.Bottle;

import org.example.TEST3.Bottle.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BottleRepo extends JpaRepository<Bottle, Integer> {

    @Query("select t from Bottle t where t.Type = ?1")
    Bottle get_quantity(String type);

    @Modifying
    @Transactional
    @Query("update Bottle t set t.Quantity = t.Quantity - ?1 where t.Type = ?2")
    void updateQuantity(Integer val, String type);
}
