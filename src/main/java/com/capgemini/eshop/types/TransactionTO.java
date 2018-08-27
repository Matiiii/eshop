package com.capgemini.eshop.types;

import java.util.Date;
import java.util.List;

import com.capgemini.eshop.enums.Status;

public class TransactionTO {

	private Long id;
	private Date date;
	private Status currentStatus;
	private List<Long> products;

	private Long customer;

	private Double sumCost;
	private int countProducts;
	private int version;
	private Date created;
	private Date updated;

	public TransactionTO() {
	}

	public TransactionTO(Long id, Date date, Status currentStatus, List<Long> products, Long customer, int version,
			Date created, Date updated) {
		this.id = id;
		this.date = date;
		this.currentStatus = currentStatus;
		this.products = products;
		this.customer = customer;
		this.version = version;
		this.created = created;
		this.updated = updated;

		this.countProducts = products.size();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<Long> getProducts() {
		return products;
	}

	public void setProducts(List<Long> products) {
		this.products = products;
		this.countProducts = products.size();
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

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public void getSumCost() {

	}

	public static OrderTOBuilder builder() {
		return new OrderTOBuilder();
	}

	public static class OrderTOBuilder {

		private Long id;
		private Date date;
		private Status currentStatus;
		private List<Long> products;
		private Long customer;

		private int version;
		private Date created = null;
		private Date updated = null;

		public OrderTOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public OrderTOBuilder date(Date date) {
			this.date = date;
			return this;
		}

		public OrderTOBuilder currentStatus(Status currentStatus) {
			this.currentStatus = currentStatus;
			return this;
		}

		public OrderTOBuilder products(List<Long> products) {
			this.products = products;
			return this;
		}

		public OrderTOBuilder customer(Long customer) {
			this.customer = customer;
			return this;
		}

		public OrderTOBuilder version(int version) {
			this.version = version;

			return this;
		}

		public OrderTOBuilder created(Date created) {
			this.created = created;
			return this;
		}

		public OrderTOBuilder updated(Date updated) {
			this.updated = updated;
			return this;
		}

		private void checkBeforeBuild() {
			if (customer == null || currentStatus == null || products.isEmpty()) {
				throw new RuntimeException("Invalid Order created");
			}
		}

		public TransactionTO build() {
			checkBeforeBuild();
			return new TransactionTO(id, date, currentStatus, products, customer, version, created, updated);

		}

	}

}
