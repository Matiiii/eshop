package com.capgemini.eshop.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.ProductEntity;
import com.capgemini.eshop.types.ProductTO;

@Component
public class ProductMapper {
	public ProductTO map(ProductEntity productEntity) {
		if (productEntity != null) {

			List<Long> ordersId = new ArrayList<>();
			if (!productEntity.getOrders().isEmpty()) {
				productEntity.getOrders().stream().forEach(orders -> ordersId.add(orders.getId()));
			}

			return ProductTO.builder().id(productEntity.getId()).name(productEntity.getName())
					.price(productEntity.getPrice()).retailMargin(productEntity.getRetailMargin())
					.weight(productEntity.getWeight()).orders(ordersId).version(productEntity.getVersion())
					.created(productEntity.getCreated()).updated(productEntity.getUpdated()).build();
		}

		return null;
	}

	public ProductEntity map(ProductTO productTO) {
		if (productTO != null) {

			ProductEntity productEntity = new ProductEntity();
			productEntity.setId(productTO.getId());
			productEntity.setName(productTO.getName());
			productEntity.setPrice(productTO.getPrice());
			productEntity.setRetailMargin(productTO.getRetailMargin());
			productEntity.setWeight(productTO.getWeight());
			productEntity.setVersion(productTO.getVersion());

			return productEntity;

		}
		return null;
	}

	public Set<ProductTO> map2To(Set<ProductEntity> productEntity) {
		return productEntity.stream().map(this::map).collect(Collectors.toSet());

	}

	public Set<ProductEntity> map2Entity(Set<ProductTO> productTO) {
		return productTO.stream().map(this::map).collect(Collectors.toSet());
	}

}
