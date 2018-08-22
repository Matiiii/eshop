package com.capgemini.eshop.domain.embeddable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@MappedSuperclass
public abstract class Audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 769182575029121512L;

	@Version
	private int version;

	@CreatedDate
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date created;

	@LastModifiedDate
	@Temporal(value = TemporalType.TIMESTAMP)
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
