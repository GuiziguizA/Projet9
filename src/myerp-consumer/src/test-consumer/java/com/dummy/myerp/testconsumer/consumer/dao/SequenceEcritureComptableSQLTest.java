package com.dummy.myerp.testconsumer.consumer.dao;

import static com.dummy.myerp.testconsumer.consumer.builder.SequenceBuilder.aSequence;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SequenceEcritureComptableSQLTest extends ConsumerTestCase {

	private ComptabiliteDaoImpl comptabiliteDao = new ComptabiliteDaoImpl();
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test(expected = NotFoundException.class)
	public void delete_sequence() {

		// GIVEN
		SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").build();

		// WHEN
		comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);
		try {
			Assert.assertNull(comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable));
		} catch (NotFoundException e) {

		}

	}

	@Test
	public void insert_sequence() {

		// GIVEN
		SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").derniereVal(49)
				.build();

		// WHEN
		comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);

		// THEN
		assertEquals(sequenceEcritureComptable.getDerniereValeur(), new Integer(49));
		sequenceEcritureComptable.setDerniereValeur(sequenceEcritureComptable.getDerniereValeur());
		comptabiliteDao.deleteSequenceEcritureComptable(sequenceEcritureComptable);
		comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);
	}

	@Test(expected = NotFoundException.class)
	public void select_sequence_with_code_and_annee() throws NotFoundException {

		// GIVEN
		SequenceEcritureComptable sequenceEcritureComptable = aSequence().annee(2016).journalCode("AC").build();
		SequenceEcritureComptable sequenceEcritureComptable1 = aSequence().annee(2019).journalCode("AC").build();

		// WHEN
		sequenceEcritureComptable = comptabiliteDao.getSequenceViaCodeAnnee(sequenceEcritureComptable);

		// THEN
		assertNotNull(sequenceEcritureComptable.getDerniereValeur());

	}

}