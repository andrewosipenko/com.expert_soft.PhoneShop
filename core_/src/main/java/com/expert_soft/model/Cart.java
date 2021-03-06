package com.expert_soft.model;


import com.expert_soft.validator.group.G_Cart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cart implements Serializable{

    public static final Cart EMPTY_CART = new Cart();

    @Valid
    @NotNull(groups = G_Cart.Item.class)
    private ConcurrentMap<Long, OrderItem> itemsMap;

    private BigDecimal subtotal;
    private Integer cartSize;

    public Cart() {
        itemsMap = new ConcurrentHashMap<>(1);
        subtotal = new BigDecimal("0");
    }

    public Map<Long, OrderItem> getItemsMap() {
        return Collections.unmodifiableMap(itemsMap);
    }

    public void setItemsMap(ConcurrentMap<Long, OrderItem> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

//    method for access order items

    public void putItem(@Valid OrderItem item){
        itemsMap.put(item.getUniqueKey(), item);
    }

    public void putItem(Long phoneId, OrderItem item){
        itemsMap.put(phoneId, item);
    }

    private void removeItem(OrderItem item){
        itemsMap.remove(item.getUniqueKey());
    }

    public OrderItem removeByPhoneKey(Long phoneKey){
        return itemsMap.remove(phoneKey);
    }

    private boolean contains(OrderItem item){
        return itemsMap.containsKey(item.getUniqueKey());
    }

    public OrderItem getItem(Long phoneKey){
        return itemsMap.get(phoneKey);
    }

    public Collection<OrderItem> getAllItems(){
        return Collections.unmodifiableCollection(itemsMap.values());
    }

    public BigDecimal recalculateSubtotal(Calculator calculator){
        return calculator.calculateSubtotal(itemsMap.values());
    }

    public Integer getCartSize() {
        return cartSize;
    }

    public void setCartSize(Integer cartSize) {
        this.cartSize = cartSize;
    }

    public int totalItemsCount(){
        int result = 0;
        for (OrderItem item: itemsMap.values()){
            result+= item.getQuantity();
        }
        return result;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart{");
        sb.append("itemsMap=").append(itemsMap);
        sb.append(", subtotal=").append(subtotal);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cart cart = (Cart) o;

        if (itemsMap != null ? !itemsMap.equals(cart.itemsMap) : cart.itemsMap != null) {
            return false;
        }
        return subtotal != null ? subtotal.equals(cart.subtotal) : cart.subtotal == null;

    }

    @Override
    public int hashCode() {
        int result = itemsMap != null ? itemsMap.hashCode() : 0;
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        return result;
    }

}
