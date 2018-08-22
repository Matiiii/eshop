package com.capgemini.eshop.service.impl;

import javax.transaction.Transactional;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.eshop.dao.OrderDao;
import com.capgemini.eshop.domain.OrderEntity;
import com.capgemini.eshop.exceptions.Message;
import com.capgemini.eshop.mappers.OrderMapper;
import com.capgemini.eshop.service.OrderService;
import com.capgemini.eshop.types.OrderTO;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDao orderRepository;

	@Autowired
	OrderMapper orderMapper;

	@Override
	public OrderTO saveNewOrder(OrderTO newOrder) {
		Preconditions.checkNotNull(newOrder, Message.EMPTY_OBJECT);

		OrderEntity orderToSave = orderMapper.map(newOrder);
		OrderEntity savedOrder = orderRepository.save(orderToSave);

		return orderMapper.map(savedOrder);
	}

	@Override
	public OrderTO findOrderById(Long id) {
		OrderEntity selectedOrder = orderRepository.findOne(id);
		return orderMapper.map(selectedOrder);
	}

}
