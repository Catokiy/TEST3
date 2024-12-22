package org.example.TEST3;

import org.example.TEST3.Bottle.Bottle;
import org.example.TEST3.Bottle.BottleRepo;
import org.example.TEST3.Order.Order;
import org.example.TEST3.Order.OrderRepo;
import org.example.TEST3.waitingOrder.waitingOrder;
import org.example.TEST3.waitingOrder.waitingOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private waitingOrderRepo waitRepo;
    @Autowired
    private BottleRepo bottleRepo;

    public List<Order> listAll(String keyword) {
        if (keyword != null) {
            return orderRepo.search(keyword);
        }
        return orderRepo.findAll();
    }
    public List<Order> listFiltered(Order filter) {
        String date = filter.getDeliveryDate();
        if (date != null & !date.isEmpty()) {
            date = date.substring(0, 4) + '-' + date.substring(5, 7) + '-' + date.substring(8);
            System.out.println(date);
            return orderRepo.searchFiltered(date);
        }
        return orderRepo.findAll();

    }
    public List<waitingOrder> waitListAll() {
        return waitRepo.findAll();
    }
    public void save(Order order) {
        this.orderRepo.save(order);
    }
    public Order findById(int id) {
        return orderRepo.findById(id).get();
    }
    public void deleteById(int id) {
        orderRepo.deleteById(id);
    }

    public void waitingSave(waitingOrder waitOrder) {
        this.waitRepo.save(waitOrder);
    }
    public waitingOrder waitingFindById(int id) {
        return waitRepo.findById(id).get();
    }
    public void waitingDeleteById(int id) {
        waitRepo.deleteById(id);
    }


    public Bottle get_glass_quantity() {
        return bottleRepo.get_quantity("стеклянная");
    }
    public Bottle get_plastic_quantity() {
        return bottleRepo.get_quantity("пластиковая");
    }

    public void updateQuantity(Integer bottleQuantity, String bottleType) {
        System.out.println(bottleRepo.get_quantity(bottleType).getQuantity());
        if (bottleQuantity <= bottleRepo.get_quantity(bottleType).getQuantity()) {
            bottleRepo.updateQuantity(bottleQuantity, bottleType);
        } else {
            throw new RuntimeException("Needed amount is bigger than available");
        }
    }
}
