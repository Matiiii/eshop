package com.capgemini.eshop.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.TransactionEntity;
import com.capgemini.eshop.types.TransactionTO;

@Component
public class TransactionMapper {

	public TransactionTO map(TransactionEntity orderEntity) {
		if (orderEntity != null) {

			List<Long> productsId = new ArrayList<>();
			if (!orderEntity.getProducts().isEmpty()) {
				orderEntity.getProducts().stream().forEach(products -> productsId.add(products.getId()));
			}

			return TransactionTO.builder().id(orderEntity.getId()).date(orderEntity.getDate())
					.currentStatus(orderEntity.getCurrentStatus()).products(productsId)
					.customer(orderEntity.getCustomer().getId()).version(orderEntity.getVersion())
					.created(orderEntity.getCreated()).updated(orderEntity.getUpdated()).build();
		}

		return null;
	}

	public TransactionEntity map(TransactionTO orderTO) {
		if (orderTO != null) {

			TransactionEntity orderEntity = new TransactionEntity();
			orderEntity.setId(orderTO.getId());
			orderEntity.setDate(orderTO.getDate());
			orderEntity.setCurrentStatus(orderTO.getCurrentStatus());
			orderEntity.setVersion(orderTO.getVersion());
			orderEntity.setSumCost();

			return orderEntity;

		}
		return null;
	}

	public Set<TransactionTO> map2To(Set<TransactionEntity> orderEntity) {
		return orderEntity.stream().map(this::map).collect(Collectors.toSet());

	}

	public Set<TransactionEntity> map2Entity(Set<TransactionTO> orderTO) {
		return orderTO.stream().map(this::map).collect(Collectors.toSet());
	}

}
