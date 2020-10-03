package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertNotNull;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public class AbstactBusinessManagerTest extends AbstractBusinessManager {

	@Before
	public void init() throws Exception {
		BusinessProxy businessProxy = Mockito.mock(BusinessProxy.class);
		DaoProxy daoProxy = () -> new FakeComptabiliteDao();
		TransactionManager transactionManager = Mockito.mock(TransactionManager.class);
		configure(businessProxy, daoProxy, transactionManager);
	}

	@Test
	public void getConstraintValidatorTest() {
		Validator vValidator = getConstraintValidator();
		assertNotNull(vValidator);
	}

	@Test
	public void getBusinessProxyTest() {
		BusinessProxy businessProxy = getBusinessProxy();
		assertNotNull(businessProxy);
	}

	@Test
	public void getDaoProxyTest() {
		DaoProxy daoProxy = getDaoProxy();
		assertNotNull(daoProxy);
	}

	@Test
	public void getTransactionManagerTest() {
		TransactionManager transactionManager = getTransactionManager();
		assertNotNull(transactionManager);
	}

}