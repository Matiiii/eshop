package com.capgemini.eshop.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.capgemini.eshop.domain.embeddable.Address;
import com.capgemini.eshop.domain.embeddable.Audit;
import com.capgemini.eshop.domain.embeddable.PersonalDetail;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CustomerEntity extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4430411239947745851L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Embedded
	private PersonalDetail personalDetail;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private Set<OrderEntity> orders = new HashSet<>();

	public CustomerEntity() {
	}

	public CustomerEntity(Long id, PersonalDetail personalDetail, Address address, Set<OrderEntity> orders) {
		this.id = id;
		this.personalDetail = personalDetail;
		this.address = address;
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonalDetail getPersonalDetail() {
		return personalDetail;
	}

	public void setPersonalDetail(PersonalDetail personalDetail) {
		this.personalDetail = personalDetail;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderEntity> orders) {
		this.orders = orders;
	}

}
