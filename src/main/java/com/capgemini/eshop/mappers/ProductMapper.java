package com.capgemini.eshop.mappers;

import java.util.HashSet;
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

	public List<ProductTO> map2To(List<ProductEntity> productEntity) {
		return productEntity.stream().map(this::map).collect(Collectors.toList());

	}

	public List<ProductEntity> map2Entity(List<ProductTO> productTO) {
		return productTO.stream().map(this::map).collect(Collectors.toList());
	}

}
