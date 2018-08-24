package com.capgemini.eshop.domain.embeddable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 769182575029121512L;

	@Version
	private int version;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date created;

	@UpdateTimestamp
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column
	private Date updated;

	public int getVersion() {
		return version;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
