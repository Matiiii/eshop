package com.capgemini.eshop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.capgemini.eshop.domain.embeddable.AbstractEntity;
import com.capgemini.eshop.enums.Status;

@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TransactionEntity extends AbstractEntity {

	private static final long serialVersionUID = 4246434942616898328L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Enumerated(EnumType.STRING)
	private Status currentStatus;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "order_product", joinColumns = {
			@JoinColumn(name = "order_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "product_id", nullable = false, updatable = false) })
	private List<ProductEntity> products = new ArrayList<>();

	@Transient
	private int countProducts;

	@ManyToOne
	private CustomerEntity customer;

	public TransactionEntity() {
	}

	public TransactionEntity(Long id, Date date, Status currentStatus, List<ProductEntity> products,
			CustomerEntity customer) {
		this.id = id;
		this.date = date;
		this.currentStatus = currentStatus;
		this.products = products;
		this.customer = customer;

		this.countProducts = products.size();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
		this.countProducts = products.size();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "TransactionEntity [id=" + id + ", date=" + date + ", currentStatus=" + currentStatus + ", products="
				+ products + ", countProducts=" + countProducts + ", customer=" + customer + "]";
	}

}
