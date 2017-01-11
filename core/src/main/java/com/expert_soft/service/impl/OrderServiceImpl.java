package com.expert_soft.service.impl;


import com.expert_soft.model.Order;
import com.expert_soft.persistence.OrderDao;
import com.expert_soft.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao dao;

    @Autowired
    public void setDao(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public Order getOrder(Long key) {
        return dao.getOrder(key);
    }

    @Override
    public void saveOrder(Order order) {
        dao.saveOrder(order);
    }

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }
}
