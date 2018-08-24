package com.capgemini.eshop.mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capgemini.eshop.domain.ProductEntity;
import com.capgemini.eshop.types.ProductTO;

@Component
public class ProductMapper {
	public ProductTO map(ProductEntity productEntity) {
		if (productEntity != null) {

			Set<Long> ordersId = new HashSet<>();
			if (!productEntity.getTransactions().isEmpty()) {
				productEntity.getTransactions().stream().forEach(orders -> ordersId.add(orders.getId()));
			}

			return ProductTO.builder().id(productEntity.getId()).name(productEntity.getName())
					.price(productEntity.getPrice()).retailMargin(productEntity.getRetailMargin())
					.weight(productEntity.getWeight()).transactions(ordersId).version(productEntity.getVersion())
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
