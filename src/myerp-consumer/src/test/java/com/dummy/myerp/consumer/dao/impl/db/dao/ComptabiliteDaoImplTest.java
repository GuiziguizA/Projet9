package com.dummy.myerp.consumer.dao.impl.db.dao;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;

public class ComptabiliteDaoImplTest {
	JdbcTemplate template;
	ComptabiliteDaoImpl comptabiliteDao;

	@Before
	public void init() {
		template = Mockito.mock(JdbcTemplate.class);
		NamedParameterJdbcTemplate nameJdbc = Mockito.mock(NamedParameterJdbcTemplate.class);
		AbstractDbConsumer abstractDbConsumer = Mockito.mock(AbstractDbConsumer.class);
		comptabiliteDao = new ComptabiliteDaoImpl(template, nameJdbc, abstractDbConsumer);

		// Remplace le travail du fichier sqlContext.xml
		comptabiliteDao.setSQLgetListCompteComptable("SQLgetListCompteComptable");
		comptabiliteDao.setSQLdeleteEcritureComptable("SQLdeleteEcritureComptable");
		comptabiliteDao.setSQLgetEcritureComptable("SQLgetEcritureComptable");
		comptabiliteDao.setSQLgetListCompteComptable("SQLgetListCompteComptable");
		comptabiliteDao.setSQLdeleteListLigneEcritureComptable("SQLdeleteListLigneEcritureComptable");
		comptabiliteDao.setSQLgetListJournalComptable("SQLgetListJournalComptable");
		comptabiliteDao.setSQLgetEcritureComptableByRef("SQLgetEcritureComptableByRef");
		comptabiliteDao.setSQLloadListLigneEcriture("SQLloadListLigneEcriture");
		comptabiliteDao.setSQLgetListEcritureComptable("SQLgetListEcritureComptable");
		comptabiliteDao.setSQLupdateEcritureComptable("SQLupdateEcritureComptable");
		comptabiliteDao.setSQLinsertListLigneEcritureComptable("SQLinsertListLigneEcritureComptable");
		comptabiliteDao.setSQLinsertEcritureComptable("SQLinsertEcritureComptable");
	}

	@Test
	public void getListCompteComptable() {

		// GIVEN
		CompteComptable compteComptable1 = new CompteComptable(2332, "compte1");
		CompteComptable compteComptable2 = new CompteComptable(4568, "compte2");
		LinkedList<CompteComptable> fakeList = new LinkedList<>();
		fakeList.add(compteComptable1);
		fakeList.add(compteComptable2);
		Mockito.when(template.query(Mockito.anyString(), Mockito.any(RowMapper.class))).thenReturn(fakeList);

		// WHEN
		List<CompteComptable> vList = comptabiliteDao.getListCompteComptable();

		// THEN
		Assert.assertNotNull(vList);
		Assert.assertEquals(vList.get(0).getNumero(), new Integer(2332));
		Assert.assertEquals(vList.get(1).getNumero(), new Integer(4568));
		Assert.assertEquals(vList.get(0).getLibelle(), "compte1");
		Assert.assertEquals(vList.get(1).getLibelle(), "compte2");
		Assert.assertEquals(vList.size(), 2);
	}

	@Test
	public void getListJournalComptable() {

		// GIVEN
		JournalComptable journalComptable1 = new JournalComptable("AC", "Achat");
		JournalComptable journalComptable2 = new JournalComptable("RT", "Retrait");
		LinkedList<JournalComptable> fakeList = new LinkedList<>();
		fakeList.add(journalComptable1);
		fakeList.add(journalComptable2);
		Mockito.when(template.query(Mockito.anyString(), Mockito.any(RowMapper.class))).thenReturn(fakeList);

		// WHEN
		List<JournalComptable> vList = comptabiliteDao.getListJournalComptable();

		// THEN
		Assert.assertNotNull(vList);
		Assert.assertEquals(vList.get(0).getCode(), "AC");
		Assert.assertEquals(vList.get(1).getCode(), "RT");
		Assert.assertEquals(vList.get(0).getLibelle(), "Achat");
		Assert.assertEquals(vList.get(1).getLibelle(), "Retrait");
		Assert.assertEquals(vList.size(), 2);
	}

	@Test
	public void getListSequenceEcritureComptable() {
		// GIVEN
		SequenceEcritureComptable seq1 = new SequenceEcritureComptable(1996, 00001);
		SequenceEcritureComptable seq2 = new SequenceEcritureComptable(1999, 00002);
		LinkedList<SequenceEcritureComptable> fausseListSeq = new LinkedList<SequenceEcritureComptable>();
		fausseListSeq.add(seq1);
		fausseListSeq.add(seq2);
		Mockito.when(template.query(Mockito.anyString(), Mockito.any(RowMapper.class))).thenReturn(fausseListSeq);
		// WHEN
		List<SequenceEcritureComptable> listSequence = comptabiliteDao.getListSequenceEcritureComptable();
		Assert.assertNotNull(listSequence);
		Assert.assertEquals(listSequence.get(0).getAnnee(), new Integer(1996));
		Assert.assertEquals(listSequence.get(0).getDerniereValeur(), new Integer(00001));
		Assert.assertEquals(listSequence.size(), 2);
	}
}