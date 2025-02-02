package com.FullstackEcommerce.Ecommerce.BinStore.order;

import com.FullstackEcommerce.Ecommerce.BinStore.exceptions.ResourceNotFoundException;


import com.FullstackEcommerce.Ecommerce.BinStore.order.modell.Order;
import com.FullstackEcommerce.Ecommerce.BinStore.order.modell.OrderItem;
import com.FullstackEcommerce.Ecommerce.BinStore.products.model.Product;
import com.FullstackEcommerce.Ecommerce.BinStore.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public Order placeOrder(Order order) {
        double totalPrice = 0;

        for (OrderItem item : order.getOrderItems()) {
            Product product = productService.getProductById(item.getProduct().getId());

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new ResourceNotFoundException("Not enough stock for product: " + product.getName());
            }

            productService.updateStockQuantity(product.getId(), item.getQuantity());

            item.setOrder(order);
            item.setPrice(product.getPrice());
            totalPrice += item.getQuantity() * item.getPrice();
        }

        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
