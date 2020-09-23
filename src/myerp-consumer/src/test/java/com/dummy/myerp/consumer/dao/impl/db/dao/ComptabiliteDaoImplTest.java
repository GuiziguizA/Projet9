package com.dummy.myerp.consumer.dao.impl.db.dao;

import static com.dummy.myerp.consumer.dao.impl.db.builder.EcritureComptableBuilder.aEcritureComptable;
import static com.dummy.myerp.consumer.dao.impl.db.builder.SequenceBuilder.aSequence;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.SequenceEcritureComptableRM;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;

public class ComptabiliteDaoImplTest {

	private ComptabiliteDaoImpl comptabiliteDao;
	private JdbcTemplate template;
	private NamedParameterJdbcTemplate nameJdbc;
	private AbstractDbConsumer abstractDbConsumer;
	private String sqlRequet = null;

	@Before
	public void init() {

		template = Mockito.mock(JdbcTemplate.class);
		nameJdbc = Mockito.mock(NamedParameterJdbcTemplate.class);
		abstractDbConsumer = Mockito.mock(AbstractDbConsumer.class);
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
		comptabiliteDao.setSQLgetSequenceViaCodeAnnee("SQLgetSequenceViaCodeAnnee");
		comptabiliteDao.setSQLdeleteSequenceEcritureComptable("SQLdeleteSequenceEcritureComptable");
		comptabiliteDao.setSQLinsertSequenceEcritureComptable("SQLinsertSequenceEcritureComptable");
		comptabiliteDao.setSQLgetListSequenceEcritureComptable("SQLgetListSequenceEcritureComptable");
		comptabiliteDao.setSQLgetEcritureComptableByRef("SQLgetEcritureComptableByRef");
	}

	@Test
	public void getInstance() {
		Assert.assertNotNull(comptabiliteDao.getInstance());
		comptabiliteDao = comptabiliteDao.getInstance();
		Assert.assertEquals(comptabiliteDao, comptabiliteDao.getInstance());
	}

	@Test
	public void getListCompteComptableTest() {

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
	public void getListJournalComptableTest() {

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
	public void getListEcritureComptable() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		EcritureComptable vEcritureComptable2 = aEcritureComptable().journal(new JournalComptable("RT", "Retrait"))
				.libelle("Libelle").ref("RT-2019/00011").date(new Date()).build();
		vEcritureComptable2.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable2.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		LinkedList<EcritureComptable> fakeList = new LinkedList<>();
		fakeList.add(vEcritureComptable1);
		fakeList.add(vEcritureComptable2);
		Mockito.when(template.query(Mockito.anyString(), Mockito.any(RowMapper.class))).thenReturn(fakeList);

		// WHEN
		List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

		// THEN
		Assert.assertNotNull(vList);
		Assert.assertEquals(vList.get(0).getLibelle(), "Libelle");
		Assert.assertEquals(vList.get(0).getReference(), "AC-2019/00001");
		Assert.assertEquals(vList.get(0).getJournal().getCode(), "AC");
		Assert.assertEquals(vList.get(0).getListLigneEcriture().size(), 2);
		Assert.assertEquals(vList.get(0).getTotalCredit(), new BigDecimal(123));
		Assert.assertEquals(vList.get(0).getTotalDebit(), new BigDecimal(123));
		Assert.assertEquals(vList.get(1).getLibelle(), "Libelle");
		Assert.assertEquals(vList.get(1).getReference(), "RT-2019/00011");
		Assert.assertEquals(vList.get(1).getJournal().getCode(), "RT");
		Assert.assertEquals(vList.get(1).getListLigneEcriture().size(), 2);
		Assert.assertEquals(vList.get(1).getTotalCredit(), new BigDecimal(123));
		Assert.assertEquals(vList.get(1).getTotalDebit(), new BigDecimal(123));
		Assert.assertEquals(vList.size(), 2);
	}

	@Test
	public void getEcritureComptable() throws NotFoundException {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		Mockito.when(nameJdbc.queryForObject(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any(RowMapper.class))).thenReturn(vEcritureComptable1);

		// WHEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(12);

		// THEN
		Assert.assertNotNull(ecritureComptable);
		Assert.assertEquals(ecritureComptable.getLibelle(), "Libelle");
		Assert.assertEquals(ecritureComptable.getReference(), "AC-2019/00001");
		Assert.assertEquals(ecritureComptable.getJournal().getCode(), "AC");
		Assert.assertEquals(ecritureComptable.getListLigneEcriture().size(), 2);
		Assert.assertEquals(ecritureComptable.getTotalCredit(), new BigDecimal(123));
		Assert.assertEquals(ecritureComptable.getTotalDebit(), new BigDecimal(123));

	}

	@Test
	public void getEcritureComptableByRef() throws NotFoundException {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		Mockito.when(nameJdbc.queryForObject(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any(RowMapper.class))).thenReturn(vEcritureComptable1);

		// WHEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef("AC-2019/00001");

		// THEN
		Assert.assertNotNull(ecritureComptable);
		Assert.assertEquals(ecritureComptable.getLibelle(), "Libelle");
		Assert.assertEquals(ecritureComptable.getReference(), "AC-2019/00001");
		Assert.assertEquals(ecritureComptable.getJournal().getCode(), "AC");
		Assert.assertEquals(ecritureComptable.getListLigneEcriture().size(), 2);
		Assert.assertEquals(ecritureComptable.getTotalCredit(), new BigDecimal(123));
		Assert.assertEquals(ecritureComptable.getTotalDebit(), new BigDecimal(123));

	}

	@Test
	public void loadListLigneEcriture() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);
		List<LigneEcritureComptable> fakeList = new LinkedList<>();
		fakeList.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		fakeList.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		Mockito.when(nameJdbc.query(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any(RowMapper.class))).thenReturn(fakeList);

		// WHEN
		comptabiliteDao.loadListLigneEcriture(vEcritureComptable1);

		// THEN
		Assert.assertNotNull(vEcritureComptable1.getListLigneEcriture());
		Assert.assertEquals(vEcritureComptable1.getListLigneEcriture().size(), 2);
		Assert.assertEquals(vEcritureComptable1.getTotalDebit(), new BigDecimal(123));
		Assert.assertEquals(vEcritureComptable1.getTotalCredit(), new BigDecimal(123));
	}

	@Test
	public void insertEcritureComptable() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);
		List<EcritureComptable> vList = new LinkedList<>();
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					vList.add(vEcritureComptable1);
					return null;
				});
		Mockito.when(abstractDbConsumer.queryGetSequenceValuePostgreSQL(Mockito.any(DataSourcesEnum.class),
				Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject())).thenReturn(12);

		// WHEN
		comptabiliteDao.insertEcritureComptable(vEcritureComptable1);

		// THEN
		Assert.assertNotNull(vList);
		Assert.assertNotNull(vEcritureComptable1.getId());
		Assert.assertEquals(vEcritureComptable1.getId(), new Integer(12));
		Assert.assertEquals(vList.get(0), vEcritureComptable1);
	}

	@Test
	public void insertListLigneEcritureComptable() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable1.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		final List<LigneEcritureComptable>[] vList = new List[] { new LinkedList<>() };
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					vList[0] = vEcritureComptable1.getListLigneEcriture();
					return null;
				});

		// WHEN
		comptabiliteDao.insertListLigneEcritureComptable(vEcritureComptable1);

		// THEN
		Assert.assertEquals(vEcritureComptable1.getListLigneEcriture().get(0).toString(), vList[0].get(0).toString());
		Assert.assertEquals(vEcritureComptable1.getListLigneEcriture().get(1).toString(), vList[0].get(1).toString());
	}

	@Test
	public void updateEcritureComptable() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = aEcritureComptable().journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2019/00001").date(new Date()).build();
		vEcritureComptable1.setId(55);

		// WHEN
		comptabiliteDao.updateEcritureComptable(vEcritureComptable1);
	}

	@Test
	public void deleteEcritureComptable() {

		// GIVEN
		EcritureComptable vEcritureComptable1 = new EcritureComptable();
		EcritureComptable vEcritureComptable2 = new EcritureComptable();
		List<EcritureComptable> fakeDbList = new LinkedList<>();
		fakeDbList.add(vEcritureComptable1);
		fakeDbList.add(vEcritureComptable2);
		List<LigneEcritureComptable> fakeList = new LinkedList<>();
		fakeList.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		fakeList.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					if (fakeDbList.size() > 1) {
						fakeDbList.remove(1);
					}
					if (!fakeList.isEmpty()) {
						fakeList.remove(1);
						fakeList.remove(0);
					}
					return null;
				});

		// WHEN
		comptabiliteDao.deleteEcritureComptable(12);

		// THEN
		Assert.assertEquals(fakeList.size(), 0);
		Assert.assertEquals(fakeDbList.size(), 1);
	}

	@Test
	public void deleteListLigneEcritureComptable() {

		// GIVEN
		List<LigneEcritureComptable> fakeList = new LinkedList<>();
		fakeList.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		fakeList.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					fakeList.remove(1);
					fakeList.remove(0);
					return null;
				});

		// WHEN
		comptabiliteDao.deleteListLigneEcritureComptable(12);

		// THEN
		Assert.assertEquals(fakeList.size(), 0);
	}

	@Test
	public void deleteSequenceEcritureComptable() {

		// GIVEN
		SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2019).derniereVal(88).journalCode("AC")
				.build();
		List<SequenceEcritureComptable> fakeList = new ArrayList<>();
		fakeList.add(sequenceEcritureComptable);
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					fakeList.remove(0);
					return null;
				});

		// WHEN
		comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);

		// THEN
		Assert.assertEquals(fakeList.size(), 0);
	}

	@Test
	public void getSequenceViaCodeAnnee() throws NotFoundException {

		// GIVEN
		List<SequenceEcritureComptable> fakeList = new LinkedList<>();
		SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().derniereVal(55).annee(2019).journalCode("FE")
				.build();

		SequenceEcritureComptable sequenceEcritureComptable2 = aSequence().derniereVal(13).annee(2019).journalCode("AC")
				.build();
		fakeList.add(sequenceEcritureComptable1);
		fakeList.add(sequenceEcritureComptable2);
		Mockito.when(nameJdbc.queryForObject(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class),
				Mockito.any(SequenceEcritureComptableRM.class))).thenReturn(fakeList.get(1));
		SequenceEcritureComptable sequence = new SequenceEcritureComptable(new Integer(2019), new Integer(55));
		sequence.setJournalCode("AC");

		// WHEN
		SequenceEcritureComptable sequenceEcritureComptable = comptabiliteDao.getSequenceViaCodeAnnee(sequence);

		// THEN
		Assert.assertNotNull(sequence);
		Assert.assertNotNull(sequenceEcritureComptable2);
		Assert.assertEquals(fakeList.size(), 2);
		Assert.assertNotNull(sequenceEcritureComptable);
		Assert.assertEquals(sequenceEcritureComptable.getAnnee(), new Integer(2019));
		Assert.assertEquals(sequenceEcritureComptable.getDerniereValeur(), new Integer(13));
		Assert.assertEquals(sequenceEcritureComptable.getJournalCode(), "AC");

	}

	@Test
	public void insertSequenceEcritureComptable() {
		Integer oldDerniereValeur = new Integer(55);
		SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().derniereVal(55).annee(2019).journalCode("FE")
				.build();
		Mockito.when(nameJdbc.update(Mockito.anyString(), Mockito.any(MapSqlParameterSource.class)))
				.then((Answer<Void>) invocationOnMock -> {
					sequenceEcritureComptable1.setDerniereValeur(new Integer(56));
					return null;
				});

		// WHEN
		comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable1);

		// THEN
		Assert.assertEquals(sequenceEcritureComptable1.getDerniereValeur(), new Integer(oldDerniereValeur + 1));
	}

	@Test
	public void setSQLgetListCompteComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListCompteComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetListCompteComptable");
	}

	@Test
	public void setSQLgetListJournalComptable() throws IllegalAccessException, NoSuchFieldException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListJournalComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetListJournalComptable");
	}

	@Test
	public void setSQLgetListEcritureComptable() throws IllegalAccessException, NoSuchFieldException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetListEcritureComptable");
	}

	@Test
	public void setSQLgetEcritureComptableByRef() throws IllegalAccessException, NoSuchFieldException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetListEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetListEcritureComptable");
	}

	@Test
	public void setSQLgetEcritureComptable() throws IllegalAccessException, NoSuchFieldException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetEcritureComptable");
	}

	@Test
	public void setSQLloadListLigneEcriture() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLloadListLigneEcriture");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLloadListLigneEcriture");
	}

	@Test
	public void setSQLinsertEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLinsertEcritureComptable");
	}

	@Test
	public void setSQLinsertListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertListLigneEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLinsertListLigneEcritureComptable");
	}

	@Test
	public void setSQLupdateEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLupdateEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLupdateEcritureComptable");
	}

	@Test
	public void setSQLdeleteEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLdeleteEcritureComptable");
	}

	@Test
	public void setSQLdeleteListLigneEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteListLigneEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLdeleteListLigneEcritureComptable");
	}

	@Test
	public void setSQLgetSequenceViaCodeAnnee() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLgetSequenceViaCodeAnnee");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLgetSequenceViaCodeAnnee");
	}

	@Test
	public void setSQLdeleteSequenceEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLdeleteSequenceEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLdeleteSequenceEcritureComptable");
	}

	@Test
	public void setSQLinsertSequenceEcritureComptable() throws NoSuchFieldException, IllegalAccessException {
		comptabiliteDao = new ComptabiliteDaoImpl();
		final Field field = comptabiliteDao.getClass().getDeclaredField("SQLinsertSequenceEcritureComptable");
		field.setAccessible(true);
		Assert.assertEquals(field.get(sqlRequet), "SQLinsertSequenceEcritureComptable");
	}

	@Test
	public void getListSequenceEcritureComptable() { // GIVEN
		SequenceEcritureComptable seq1 = new SequenceEcritureComptable(1996, 00001);
		SequenceEcritureComptable seq2 = new SequenceEcritureComptable(1999, 00002);
		LinkedList<SequenceEcritureComptable> fausseListSeq = new LinkedList<SequenceEcritureComptable>();
		fausseListSeq.add(seq1);
		fausseListSeq.add(seq2);
		Mockito.when(template.query(Mockito.anyString(), Mockito.any(RowMapper.class))).thenReturn(fausseListSeq); // WHEN
		List<SequenceEcritureComptable> listSequence = comptabiliteDao.getListSequenceEcritureComptable();
		Assert.assertNotNull(listSequence);
		Assert.assertEquals(listSequence.get(0).getAnnee(), new Integer(1996));
		Assert.assertEquals(listSequence.get(0).getDerniereValeur(), new Integer(00001));
		Assert.assertEquals(listSequence.size(), 2);
	}

}