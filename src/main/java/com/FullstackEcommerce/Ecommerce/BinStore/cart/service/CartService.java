package com.FullstackEcommerce.Ecommerce.BinStore.cart.service;

import com.FullstackEcommerce.Ecommerce.BinStore.cart.model.CartItem;

import com.FullstackEcommerce.Ecommerce.BinStore.cart.repositroy.CartItemRepository;
import com.FullstackEcommerce.Ecommerce.BinStore.products.model.Product;
import com.FullstackEcommerce.Ecommerce.BinStore.products.service.ProductService;
import com.FullstackEcommerce.Ecommerce.BinStore.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductService productService;


    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }


    public CartItem addProductToCart(Long productId, int quantity) {

        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + productId + " not found.");
        }


        if (product.getStockQuantity() < quantity) {
            throw new ResourceNotFoundException("Not enough stock available for product ID " + productId);
        }


        productService.updateStockQuantity(productId, quantity);


        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }


    public void removeProductFromCart(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem with ID " + cartItemId + " not found."));


        Product product = cartItem.getProduct();
        productService.restoreStockQuantity(product.getId(), cartItem.getQuantity());


        cartItemRepository.delete(cartItem);
    }


    public void clearCart() {
        List<CartItem> cartItems = cartItemRepository.findAll();


        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            productService.restoreStockQuantity(product.getId(), cartItem.getQuantity());
        }


        cartItemRepository.deleteAll();
    }
}
