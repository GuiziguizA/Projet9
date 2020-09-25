package com.dummy.myerp.testconsumer.consumer.dao;

import static com.dummy.myerp.testconsumer.consumer.builder.EcritureComptableBuilder.aEcritureComptable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EcritureComptableSQLTest extends ConsumerTestCase {

	ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();

	private static String REF = "AC-2019/00111";

	@Test
	public void a_create_ecritureComptable_test() throws NotFoundException {

		// GIVEN
		EcritureComptable ecritureComptable = aEcritureComptable().journal(new JournalComptable("AC", "Achat")).ref(REF)
				.libelle("Achat").date(new Date()).build();

		// WHEN
		comptabiliteDao.insertEcritureComptable(ecritureComptable);
		EcritureComptable ecriture = comptabiliteDao.getEcritureComptableByRef(ecritureComptable.getReference());

		// THEN
		assertEquals(ecriture.getJournal().getCode(), "AC");
		assertEquals(ecriture.getJournal().getLibelle(), "Achat");
		assertEquals(ecriture.getLibelle(), "Achat");
	}

	@Test
	public void b_select_ecritureComptable_by_id_test() throws NotFoundException {

		// GIVEN
		Integer id = comptabiliteDao.getEcritureComptableByRef(REF).getId();

		// WHEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

		// THEN
		assertNotNull(ecritureComptable);
		assertEquals(ecritureComptable.getId(), id);
		assertEquals(ecritureComptable.getReference(), REF);
		assertEquals(ecritureComptable.getLibelle(), "Achat");
	}

	@Test
	public void c_select_ecritureComptable_by_ref_test() throws NotFoundException {

		// WHEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

		// THEN
		assertNotNull(ecritureComptable);
		assertEquals(ecritureComptable.getLibelle(), "Achat");
	}

	@Test
	public void d_insert_ligneEcriture_test() throws NotFoundException {

		// GIVEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), "yoyoyo",
				new BigDecimal(123), new BigDecimal(123)));
		ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411), "OUloulou",
				new BigDecimal(123), new BigDecimal(123)));

		// WHEN
		comptabiliteDao.insertListLigneEcritureComptable(ecritureComptable);
		EcritureComptable ecritureComptable2 = comptabiliteDao.getEcritureComptable(ecritureComptable.getId());
		comptabiliteDao.loadListLigneEcriture(ecritureComptable2);

		// THEN
		assertEquals(ecritureComptable2.getListLigneEcriture().size(), 2);
	}

	@Test
	public void e_load_ligneEcriture_test() throws NotFoundException {

		// GIVEN
		Integer id = comptabiliteDao.getEcritureComptableByRef(REF).getId();

		// GIVEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);

		// WHEN
		comptabiliteDao.loadListLigneEcriture(ecritureComptable);
	}

	public void f_select_list_ecritureComptable_test() {

		// WHEN
		List<EcritureComptable> vList = comptabiliteDao.getListEcritureComptable();

		// THEN
		assertNotNull(vList);
		assertTrue(vList.size() != 0);
	}

	@Test
	public void g_delete_ligne_ecritureComptable_test() throws NotFoundException {

		// GIVEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

		// WHEN
		comptabiliteDao.deleteListLigneEcritureComptable(ecritureComptable.getId());

		// THEN
		EcritureComptable ecritureComptable1 = comptabiliteDao.getEcritureComptableByRef(REF);
		assertEquals(ecritureComptable1.getListLigneEcriture().size(), 0);
	}

	@Test(expected = NotFoundException.class)
	public void h_delete_ecritureComptable_test() throws NotFoundException {

		// GIVEN
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef(REF);

		// WHEN
		comptabiliteDao.deleteEcritureComptable(ecritureComptable.getId());

		// THEN
		comptabiliteDao.getEcritureComptableByRef(REF);
	}

}
