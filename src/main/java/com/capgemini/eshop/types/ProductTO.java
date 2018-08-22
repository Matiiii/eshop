package com.capgemini.eshop.types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductTO {

	private Long id;
	private String name;
	private Double price;
	private Double retailMargin;
	private int weightInGrams;

	private List<Long> orders = new ArrayList<>();

	private int version;
	private Date created;
	private Date updated;

	public ProductTO() {
	}

	public ProductTO(Long id, String name, Double price, Double retailMargin, int weightInGrams, List<Long> orders,
			int version, Date created, Date updated) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.retailMargin = retailMargin;
		this.weightInGrams = weightInGrams;
		this.orders = orders;
		this.version = version;
		this.created = created;
		this.updated = updated;
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

	public int getWeightInGrams() {
		return weightInGrams;
	}

	public void setWeightInGrams(int weightInGrams) {
		this.weightInGrams = weightInGrams;
	}

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public static ProductTOBuilder builder() {
		return new ProductTOBuilder();
	}

	public static class ProductTOBuilder {

		private Long id;
		private String name;
		private Double price;
		private Double retailMargin;
		private int weightInGrams;

		private List<Long> orders = new ArrayList<>();

		private int version;
		private Date created = null;
		private Date updated = null;

		public ProductTOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public ProductTOBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ProductTOBuilder price(Double price) {
			this.price = price;
			return this;
		}

		public ProductTOBuilder retailMargin(Double retailMargin) {
			this.retailMargin = retailMargin;
			return this;
		}

		public ProductTOBuilder orders(List<Long> orders) {
			this.orders = orders;
			return this;
		}

		public ProductTOBuilder weightInGrams(int weightInGrams) {
			this.weightInGrams = weightInGrams;

			return this;
		}

		public ProductTOBuilder version(int version) {
			this.version = version;

			return this;
		}

		public ProductTOBuilder created(Date created) {
			this.created = created;
			return this;
		}

		public ProductTOBuilder updated(Date updated) {
			this.updated = updated;
			return this;
		}

		private void checkBeforeBuild() {
			if (name == null || retailMargin == null || price == null || orders.isEmpty() || weightInGrams < 0) {
				throw new RuntimeException("Invalid product created");
			}
		}

		public ProductTO build() {
			checkBeforeBuild();
			return new ProductTO(id, name, price, retailMargin, weightInGrams, orders, version, created, updated);

		}

	}

}
