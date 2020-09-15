package com.dummy.myerp.consumer.dao.impl;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;

public class DaoProxyImplTest {

	@Test
	public void getComptabiliteDao() {

		// GIVEN
		DaoProxyImpl daoProxy = new DaoProxyImpl();
		daoProxy.setComptabiliteDao(mock(ComptabiliteDao.class));

		// WHEN
		ComptabiliteDao comptabiliteDao = daoProxy.getComptabiliteDao();

		// THEN
		Assert.assertNotNull(comptabiliteDao);
	}

}
