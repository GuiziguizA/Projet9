package com.dummy.myerp.testconsumer.consumer.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;

public class JournalComptableSqlTest extends ConsumerTestCase {

	ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

	@Test
	public void select_list_journalComptable_test() {

		List<JournalComptable> listJournal = comptabiliteDao.getListJournalComptable();

		Assert.assertNotNull(listJournal);

	}

}
