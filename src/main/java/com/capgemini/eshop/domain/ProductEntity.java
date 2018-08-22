package com.capgemini.eshop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.capgemini.eshop.domain.embeddable.Audit;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProductEntity extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5396366919956075720L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Double retailMargin;

	private Double price;

	private Integer weight;

	@ManyToMany(mappedBy = "products")
	private List<OrderEntity> orders = new ArrayList<>();

	public ProductEntity() {
	}

	public ProductEntity(Long id, String name, Double price, Double retailMargin, Integer weight,
			List<OrderEntity> orders) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.retailMargin = retailMargin;
		this.weight = weight;
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getRetailMargin() {
		return retailMargin;
	}

	public void setRetailMargin(Double retailMargin) {
		this.retailMargin = retailMargin;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

}
