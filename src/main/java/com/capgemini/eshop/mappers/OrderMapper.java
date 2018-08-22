package com.capgemini.eshop.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.OrderEntity;
import com.capgemini.eshop.types.OrderTO;

@Component
public class OrderMapper {

	public OrderTO map(OrderEntity orderEntity) {
		if (orderEntity != null) {

			List<Long> productsId = new ArrayList<>();
			if (!orderEntity.getProducts().isEmpty()) {
				orderEntity.getProducts().stream().forEach(products -> productsId.add(products.getId()));
			}

			return OrderTO.builder().id(orderEntity.getId()).date(orderEntity.getDate())
					.currentStatus(orderEntity.getCurrentStatus()).products(productsId)
					.customer(orderEntity.getCustomer().getId()).version(orderEntity.getVersion())
					.created(orderEntity.getCreated()).updated(orderEntity.getUpdated()).build();
		}

		return null;
	}

	public OrderEntity map(OrderTO orderTO) {
		if (orderTO != null) {

			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setId(orderTO.getId());
			orderEntity.setDate(orderTO.getDate());
			orderEntity.setCurrentStatus(orderTO.getCurrentStatus());
			orderEntity.setVersion(orderTO.getVersion());

			return orderEntity;

		}
		return null;
	}

	public Set<OrderTO> map2To(Set<OrderEntity> orderEntity) {
		return orderEntity.stream().map(this::map).collect(Collectors.toSet());

	}

	public Set<OrderEntity> map2Entity(Set<OrderTO> orderTO) {
		return orderTO.stream().map(this::map).collect(Collectors.toSet());
	}

}
