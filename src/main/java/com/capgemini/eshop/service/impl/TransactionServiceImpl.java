package com.capgemini.eshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.eshop.dao.CustomerDao;
import com.capgemini.eshop.dao.ProductDao;
import com.capgemini.eshop.dao.TransactionDao;
import com.capgemini.eshop.dao.impl.TransactionSearchCriteria;
import com.capgemini.eshop.domain.CustomerEntity;
import com.capgemini.eshop.domain.ProductEntity;
import com.capgemini.eshop.domain.TransactionEntity;
import com.capgemini.eshop.enums.Status;
import com.capgemini.eshop.exceptions.Message;
import com.capgemini.eshop.mappers.TransactionMapper;
import com.capgemini.eshop.service.TransactionService;
import com.capgemini.eshop.types.TransactionTO;

@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionDao transactionRepository;

	@Autowired
	CustomerDao customerRepository;

	@Autowired
	ProductDao productRepository;

	@Autowired
	TransactionMapper transactionMapper;

	@Override
	public TransactionTO saveNewTransaction(TransactionTO newTransaction) {

		Preconditions.checkNotNull(newTransaction, Message.EMPTY_OBJECT);
		checkCustomer(newTransaction.getCustomer());
		checkProductList(newTransaction.getProducts(), newTransaction.getCustomer());

		TransactionEntity orderToSave = transactionMapper.map(newTransaction);
		orderToSave.setCustomer(customerRepository.findOne(newTransaction.getCustomer()));

		List<ProductEntity> productsToAdd = new ArrayList<>();
		newTransaction.getProducts().stream().forEach(product -> productsToAdd.add(productRepository.findOne(product)));
		orderToSave.getProducts().addAll(productsToAdd);

		TransactionEntity savedOrder = transactionRepository.save(orderToSave);

		CustomerEntity customerToUpdate = savedOrder.getCustomer();
		customerToUpdate.getOrders().add(savedOrder);
		customerRepository.save(customerToUpdate);

		savedOrder.getProducts().forEach(product -> product.getTransactions().add(savedOrder));
		productRepository.save(savedOrder.getProducts().stream().collect(Collectors.toSet()));

		return transactionMapper.map(savedOrder);
	}

	private void checkCustomer(Long customerId) {
		if (!customerRepository.exists(customerId)) {
			throw new RuntimeException("Customer not exist");
		}

	}

	private void isProductExist(Long productId) {

		if (!productRepository.exists(productId)) {
			throw new RuntimeException("Product not exist :" + productId);
		}
	}

	private Double sumPriceProducts(List<Long> products) {

		List<ProductEntity> productPrice = new ArrayList<>();
		products.stream().forEach(productId -> productPrice.add(productRepository.findOne(productId)));
		Double sum = productPrice.stream().mapToDouble(productEntity -> productEntity.getPriceWithMargin()).sum();

		return sum;
	}

	private boolean isMoreLike5SameProductsOver7000(List<Long> products) {
		Set<Long> productsOver7000 = products.stream().map(productId -> productRepository.findOne(productId))
				.filter(product -> product.getPriceWithMargin() > 7000).map(product -> product.getId())
				.collect(Collectors.toSet());
		productsOver7000.stream().map(productIdOver7000 -> products.stream()
				.filter(productId -> productId.equals(productIdOver7000)).count());
		if (productsOver7000.stream().anyMatch(count -> count > 5)) {
			return true;
		}

		return false;

	}

	private void checkProductList(List<Long> products, Long customerId) {

		products.stream().forEach(productId -> isProductExist(productId));

		if (isMoreLike5SameProductsOver7000(products)) {

			throw new RuntimeException("The transaction has more than 5 of the same products more expensive than 7000");

		}

		if (sumPriceProducts(products) > 5000 && countRealizedTransactionsByCustomerId(customerId) < 3) {
			throw new RuntimeException("Customer don't have required number of realized transaction");
		}

	}

	@Override
	public TransactionTO findTransactionById(Long id) {
		TransactionEntity selectedOrder = transactionRepository.findOne(id);
		return transactionMapper.map(selectedOrder);
	}

	@Override
	public void removeTransaction(Long id) {

		if (transactionRepository.exists(id)) {

			TransactionEntity transactionToRemove = transactionRepository.findOne(id);

			CustomerEntity customerToUpdate = transactionToRemove.getCustomer();
			Set<ProductEntity> productsToUpdate = transactionToRemove.getProducts().stream()
					.collect(Collectors.toSet());

			customerToUpdate.getOrders().remove(transactionToRemove);
			productsToUpdate.stream().forEach(product -> product.getTransactions().remove(transactionToRemove));

			customerRepository.save(customerToUpdate);
			productRepository.save(productsToUpdate);
			transactionRepository.delete(id);

		} else {
			throw new RuntimeException("Transaction not exist");
		}

	}

	@Override
	public Long countRealizedTransactionsByCustomerId(Long id) {

		CustomerEntity customer = customerRepository.findOne(id);

		return transactionRepository.countByCustomerAndCurrentStatus(customer, Status.COMPLETED);

	}

	@Override
	public TransactionTO updateTransaction(TransactionTO newTransaction) {

		return saveNewTransaction(newTransaction);

	}

	@Override
	public Set<TransactionTO> findTransaktionsByCustomerId(Long customerId) {

		return transactionMapper.map2To(
				transactionRepository.findTransactionByCustomerId(customerId).stream().collect(Collectors.toSet()));

	}

	@Override
	public Set<TransactionTO> findTransactionByCriteria(TransactionSearchCriteria criteria) {
		Set<TransactionEntity> result = transactionRepository.findTransactionByCritria(criteria).stream()
				.collect(Collectors.toSet());
		return transactionMapper.map2To(result);

	}

}
