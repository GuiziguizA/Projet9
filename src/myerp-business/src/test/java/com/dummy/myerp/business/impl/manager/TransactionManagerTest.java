package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import com.dummy.myerp.business.impl.TransactionManager;

public class TransactionManagerTest extends TransactionManager {

	private TransactionManager transactionManager;

	@Before
	public void init() {
		PlatformTransactionManager platformTransactionManager = Mockito.mock(PlatformTransactionManager.class);
		Mockito.when(platformTransactionManager.getTransaction(Mockito.anyObject())).thenReturn(null);
		transactionManager = new TransactionManager(platformTransactionManager);
	}

	@Test
	public void getInstanceTest() {
		TransactionManager tm = getInstance();
		assertNotNull(tm);
	}

	@Test
	public void beginTransactionMyERPTest() {
		TransactionStatus transa = transactionManager.beginTransactionMyERP();
		assertNull(transa);
	}

	@Test
	public void commitMyERP() {
		TransactionStatus ts = Mockito.mock(TransactionStatus.class);
		transactionManager.commitMyERP(ts);
	}

	@Test
	public void rollbackMyERP() {
		TransactionStatus ts = Mockito.mock(TransactionStatus.class);
		transactionManager.rollbackMyERP(ts);
	}

}