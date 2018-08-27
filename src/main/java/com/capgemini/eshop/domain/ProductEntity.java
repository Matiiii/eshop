package com.capgemini.eshop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.capgemini.eshop.domain.embeddable.AbstractEntity;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProductEntity extends AbstractEntity {

	private static final long serialVersionUID = 5396366919956075720L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Double retailMargin;

	private Double price;

	private Integer weight;

	private Double priceWithMargin;

	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private Set<TransactionEntity> transactions = new HashSet<>();

	public ProductEntity() {
	}

	public ProductEntity(Long id, String name, Double price, Double retailMargin, Integer weight,
			Set<TransactionEntity> transactions) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.retailMargin = retailMargin;
		this.weight = weight;
		this.transactions = transactions;
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
		if (retailMargin != null) {
			setPriceWithMargin();
		}
	}

	public Double getRetailMargin() {
		return retailMargin;
	}

	public void setRetailMargin(Double retailMargin) {
		this.retailMargin = retailMargin;
		if (price != null) {
			setPriceWithMargin();
		}
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Set<TransactionEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<TransactionEntity> transactions) {
		this.transactions = transactions;
	}

	public Double getPriceWithMargin() {
		return priceWithMargin;
	}

	public void setPriceWithMargin() {
		this.priceWithMargin = price + (price * (retailMargin / 100));
	}

	@Override
	public String toString() {
		return "ProductEntity [id=" + id + ", name=" + name + ", retailMargin=" + retailMargin + ", price=" + price
				+ ", weight=" + weight + ", transactions=" + transactions + "]";
	}

}
