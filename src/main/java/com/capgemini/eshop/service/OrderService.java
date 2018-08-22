package com.capgemini.eshop.service;

import com.capgemini.eshop.types.OrderTO;

public interface OrderService {

	OrderTO saveNewOrder(OrderTO newOrder);

	OrderTO findOrderById(Long id);

}
