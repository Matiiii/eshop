package com.capgemini.eshop.dao.impl;

import java.util.Date;

public class TransactionSearchCriteria {

	private String customerName;

	private Date transactionStart;

	private Date transactionStop;

	private Long productId;

	private Double costMin;

	private Double costMax;

	public TransactionSearchCriteria() {
	}

	public TransactionSearchCriteria(String customerName, Date transactionStart, Date transactionStop, Long productId,
			Double costMin, Double costMax) {
		this.customerName = customerName;
		this.transactionStart = transactionStart;
		this.transactionStop = transactionStop;
		this.productId = productId;
		this.costMin = costMin;
		this.costMax = costMax;

		checkCriteria();
	}

	public void checkCriteria() {
		if ((costMin != null && costMax == null) || (costMin == null && costMax != null)
				|| (costMin != null && costMax != null && costMin > costMax)) {
			throw new IllegalArgumentException();
		}

		if ((transactionStart != null && transactionStop == null)
				|| (transactionStart == null && transactionStop != null)
				|| (transactionStart != null && transactionStop != null && transactionStart.after(transactionStop))) {
			throw new IllegalArgumentException();
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productName) {
		this.productId = productName;
	}

	public Double getCostMin() {
		return costMin;
	}

	public void setCostMin(Double costMin) {
		this.costMin = costMin;

	}

	public Double getCostMax() {
		return costMax;
	}

	public void setCostMax(Double costMax) {
		this.costMax = costMax;

	}

	public Date getTransactionStart() {
		return transactionStart;
	}

	public void setTransactionStart(Date transactionStart) {
		this.transactionStart = transactionStart;
	}

	public Date getTransactionStop() {
		return transactionStop;
	}

	public void setTransactionStop(Date transactionStop) {
		this.transactionStop = transactionStop;
	}

}
