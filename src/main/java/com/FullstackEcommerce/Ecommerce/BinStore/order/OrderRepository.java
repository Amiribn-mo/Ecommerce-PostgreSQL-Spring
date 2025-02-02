package com.FullstackEcommerce.Ecommerce.BinStore.order;

import com.FullstackEcommerce.Ecommerce.BinStore.order.modell.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String email);
}
