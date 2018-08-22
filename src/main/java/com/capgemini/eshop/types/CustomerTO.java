package com.capgemini.eshop.types;

import java.util.Date;
import java.util.Set;

import com.capgemini.eshop.domain.embeddable.Address;
import com.capgemini.eshop.domain.embeddable.PersonalDetail;

public class CustomerTO {

	private Long id;
	private PersonalDetail personalDetail;
	private Address address;

	private Set<Long> orders;

	private int version;
	private Date created;
	private Date updated;

	public CustomerTO() {
	}

	public CustomerTO(Long id, PersonalDetail personalDetail, Address address, Set<Long> orders, int version,
			Date created, Date updated) {
		this.id = id;
		this.personalDetail = personalDetail;
		this.address = address;
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

	public Set<Long> getOrders() {
		return orders;
	}

	public void setOrders(Set<Long> orders) {
		this.orders = orders;
	}

	public static CustomerTOBuilder builder() {
		return new CustomerTOBuilder();
	}

	public static class CustomerTOBuilder {

		private Long id;
		private PersonalDetail personalDetail;
		private Address address;

		private Set<Long> orders;

		private int version;
		private Date created = null;
		private Date updated = null;

		public CustomerTOBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public CustomerTOBuilder personalDetail(PersonalDetail personalDetail) {
			this.personalDetail = personalDetail;
			return this;
		}

		public CustomerTOBuilder address(Address address) {
			this.address = address;
			return this;
		}

		public CustomerTOBuilder version(int version) {
			this.version = version;

			return this;
		}

		public CustomerTOBuilder created(Date created) {
			this.created = created;
			return this;
		}

		public CustomerTOBuilder updated(Date updated) {
			this.updated = updated;
			return this;
		}

		public CustomerTOBuilder orders(Set<Long> orders) {
			this.orders = orders;
			return this;
		}

		private void checkBeforeBuild() {
			if (!personalDetail.checkPersonalDetail() || !address.checkAddress()) {
				throw new RuntimeException("Invalid customer created");
			}
		}

		public CustomerTO build() {
			checkBeforeBuild();
			return new CustomerTO(id, personalDetail, address, orders, version, created, updated);

		}

	}

}
