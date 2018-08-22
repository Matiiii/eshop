package com.capgemini.eshop.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.types.CustomerTO;

@Component
public class CustomerMapper {

	public CustomerTO map(CustomerEntity customerEntity) {
		if (customerEntity != null) {

			Set<Long> ordersId = new HashSet<>();
			if (!customerEntity.getOrders().isEmpty()) {
				customerEntity.getOrders().stream().forEach(orders -> ordersId.add(orders.getId()));
			}

			return CustomerTO.builder().id(customerEntity.getId()).personalDetail(customerEntity.getPersonalDetail())
					.address(customerEntity.getAddress()).orders(ordersId).version(customerEntity.getVersion())
					.created(customerEntity.getCreated()).updated(customerEntity.getUpdated()).build();
		}

		return null;
	}

	public CustomerEntity map(CustomerTO customerTO) {
		if (customerTO != null) {

			CustomerEntity customerEntity = new CustomerEntity();
			customerEntity.setId(customerTO.getId());
			customerEntity.setPersonalDetail(customerTO.getPersonalDetail());
			customerEntity.setAddress(customerTO.getAddress());
			customerEntity.setVersion(customerTO.getVersion());

			return customerEntity;

		}
		return null;
	}

	public Set<CustomerTO> map2To(Set<CustomerEntity> customerEntity) {
		return customerEntity.stream().map(this::map).collect(Collectors.toSet());

	}

	public Set<CustomerEntity> map2Entity(Set<CustomerTO> customerTO) {
		return customerTO.stream().map(this::map).collect(Collectors.toSet());
	}

}
