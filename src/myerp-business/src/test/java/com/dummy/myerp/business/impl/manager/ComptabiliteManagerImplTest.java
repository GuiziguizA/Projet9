
package com.dummy.myerp.business.impl.manager;

import static com.dummy.myerp.business.impl.manager.builder.EcritureComptableBuilder.aEcritureComptable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.transaction.TransactionStatus;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

public class ComptabiliteManagerImplTest {

	private ComptabiliteManagerImpl manager = new ComptabibliteManagerImplFake();
	private FakeComptabiliteDao fakeComptabiliteDao = new FakeComptabiliteDao();
	private EcritureComptable vEcritureComptable;

	@Test
	public void addReference() throws NotFoundException, FunctionalException {

		// GIVEN
		String oldRef = "AC-2020/00001";
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref(oldRef).build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		// WHEN
		manager.addReference(vEcritureComptable);

		// THEN
		assertNotEquals(oldRef, vEcritureComptable.getReference());
		vEcritureComptable.setJournal(new JournalComptable("WW", "TestThrow"));
	}

	@Test
	public void getListCompteComptable() {

		// WHEN
		List<CompteComptable> vList = manager.getListCompteComptable();

		// THEN
		assertNotNull(vList);
		assertEquals(vList.size(), 2);
	}

	@Test
	public void getListJournalComptable() {

		// WHEN
		List<JournalComptable> vList = manager.getListJournalComptable();

		// THEN
		assertNotNull(vList);
		assertEquals(vList.size(), 2);
	}

	@Test
	public void getListEcritureComptable() {

		// WHEN
		List<EcritureComptable> vList = manager.getListEcritureComptable();

		// THEN
		assertNotNull(vList);
		assertEquals(vList.size(), 2);
	}

	@Test
	public void checkEcritureComptableUnit() throws FunctionalException {

		// GIVEN
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.setReference("AC-2020/00001");
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		// WHEN
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitViolation() throws FunctionalException {

		vEcritureComptable = new EcritureComptable();
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void checkEcritureComptableUnitRG2() throws FunctionalException {
		expectedEx.expect(FunctionalException.class);
		expectedEx.expectMessage("L'écriture comptable n'est pas équilibrée.");

		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("BQ-2020/00001").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));
		manager.checkEcritureComptable(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableUnitRG3() throws FunctionalException {
		expectedEx.expect(FunctionalException.class);
		expectedEx.expectMessage(
				"L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");

		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2020/00002").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(0)));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(0)));
		manager.checkEcritureComptable(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableUnitRG5annee() throws FunctionalException {
		expectedEx.expect(FunctionalException.class);
		expectedEx.expectMessage("L'année dans la référence doit correspondre à la date de l'écriture.");
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2015/00001").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		vEcritureComptable.setReference("AC-2034/00041");

		manager.checkEcritureComptable(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableUnitRG5Ref() throws FunctionalException {
		expectedEx.expect(FunctionalException.class);
		expectedEx.expectMessage("Le code journal dans la référence doit correspondre à celui du code journal.");
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("BQ-2020/00001").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		vEcritureComptable.setReference("BQ-2020/00041");

		manager.checkEcritureComptable(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableUnitRG5null() throws FunctionalException {
		expectedEx.expect(FunctionalException.class);
		expectedEx.expectMessage("La référence est null");
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("BQ-2020/00001").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		vEcritureComptable.setReference(null);

		manager.checkEcritureComptable(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG6() throws FunctionalException {

		// GIVEN
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2020/00001").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		// WHEN
		manager.checkEcritureComptableContext(vEcritureComptable);

	}

	@Test
	public void deleteEcritureComptable() {

		// GIVEN
		Integer listSize = manager.getListEcritureComptable().size();

		// WHEN
		manager.deleteEcritureComptable(45);

		// THEN
		assertEquals(listSize - 1, manager.getListEcritureComptable().size());

	}

	@Test
	public void updateEcritureComptable() throws FunctionalException {

		// GIVEN
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2020/02201").build();
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, null, new BigDecimal(123)));

		// WHEN
		manager.updateEcritureComptable(vEcritureComptable);
	}

	@Test
	public void insertEcritureComptable() throws FunctionalException {

		// GIVEN
		Integer listSize = manager.getListEcritureComptable().size();
		vEcritureComptable = aEcritureComptable().date(new Date()).journal(new JournalComptable("AC", "Achat"))
				.libelle("Libelle").ref("AC-2020/00001").build();
		vEcritureComptable.setId(45);
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
		vEcritureComptable.getListLigneEcriture()
				.add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));

		// WHEN
		manager.insertEcritureComptable(vEcritureComptable);

		// THEN
		assertEquals(listSize + 1, manager.getListEcritureComptable().size());
	}

	@Test
	public void insertSequenceEcritureComptable() {

		SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(new Integer(2019),
				new Integer(59));
		sequenceEcritureComptable.setJournalCode("AC");

		// WHEN
		manager.insertSequenceEcritureComptable(sequenceEcritureComptable);
	}

	@Test
	public void daoProxyTest() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		DaoProxy daoProxy = manager.daoProxy();
		assertNotNull(daoProxy);
	}

	@Test
	public void daoProxy() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		DaoProxy daoProxy = manager.getDaoProxy();
		assertNotNull(daoProxy);
	}

	@Test
	public void getvTS() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		manager.getvTS();
	}

	@Test
	public void rollbackMyERP() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		manager.rollbackMyERP(manager.getvTS());
	}

	@Test
	public void commitMyERP() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		manager.commitMyERP(manager.getvTS());
	}

	@Test
	public void getValidate() {
		manager = new ComptabiliteManagerImpl();
		init(manager);
		Set<ConstraintViolation<EcritureComptable>> constraintViolations = manager.getValidate(new EcritureComptable());
		assertNotNull(constraintViolations);
	}

	public void init(ComptabiliteManagerImpl vManager) {
		BusinessProxy businessProxy = mock(BusinessProxy.class);
		DaoProxy daoProxy = () -> new FakeComptabiliteDao();
		TransactionManager transactionManager = mock(TransactionManager.class);
		vManager.configure(businessProxy, daoProxy, transactionManager);
	}

	private class ComptabibliteManagerImplFake extends ComptabiliteManagerImpl {

		@Override
		protected DaoProxy daoProxy() {
			return () -> fakeComptabiliteDao;
		}

		@Override
		protected TransactionStatus getvTS() {
			return mock(TransactionStatus.class);
		}

		@Override
		protected void rollbackMyERP(TransactionStatus vTS) {
		}

		@Override
		protected void commitMyERP(TransactionStatus vTS) {
		}

		@Override
		protected Set<ConstraintViolation<EcritureComptable>> getValidate(EcritureComptable pEcritureComptable) {
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			Set<ConstraintViolation<EcritureComptable>> valide = validator.validate(pEcritureComptable);
			if (!valide.isEmpty()) {
				valide.clear();
			}
			return valide;
		}
	}
}