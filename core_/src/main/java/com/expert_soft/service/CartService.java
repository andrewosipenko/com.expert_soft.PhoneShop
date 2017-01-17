package com.expert_soft.service;


import com.expert_soft.model.Cart;
import com.expert_soft.model.OrderItem;
import com.expert_soft.model.Phone;

public interface CartService {

    /**
     * @param cart - current cart
     * @param phone - phone to be saved
     * @param quantity - quantity of Phones
     * @return whether cart contains same phone
     */
    boolean addToCart(Cart cart, Phone phone, Long quantity);
    Phone addToCart(Cart cart, Long phoneId, Long quantity);
    OrderItem deleteFromCart(Cart cart, Phone phone, Long quantity);
    OrderItem deleteFromCart(Cart cart, Long phoneId, Long quantity);

}