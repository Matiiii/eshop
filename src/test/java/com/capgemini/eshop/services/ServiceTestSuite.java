package com.capgemini.eshop.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TransactionServiceTest.class, CustomerServiceTest.class, ProductServiceTest.class })
public class ServiceTestSuite {

}
