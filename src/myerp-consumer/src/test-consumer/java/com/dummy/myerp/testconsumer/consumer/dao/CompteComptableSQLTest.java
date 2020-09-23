package com.dummy.myerp.testconsumer.consumer.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;

public class CompteComptableSQLTest extends ConsumerTestCase {

	ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

	@Before
	public void init() {
	}

	@Test
	public void select_list_compteComptable_test() {

		// WHEN
		List<CompteComptable> compteComptable = comptabiliteDao.getListCompteComptable();

		// THEN
		assertNotNull(compteComptable);
	}
}