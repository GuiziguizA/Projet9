package com.dummy.myerp.consumer.dao.impl.cache;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

public class CompteComptableDaoCacheTest {

	CompteComptableDaoCache compteComptableDaoCache;

	@Test
	public void getByNumero() {

		// GIVEN
		Integer numero = new Integer(2332);

		// WHEN
		CompteComptable compteComptable = compteComptableDaoCache.getByNumero(numero);

		// THEN
		Assert.assertNotNull(compteComptable);
		Assert.assertEquals(compteComptable.getNumero(), numero);
	}

	@Test
	public void getByNumeroCompte() {

		// WHEN
		compteComptableDaoCache = new CompteComptableDaoCache();
		CompteComptable compteComptable = compteComptableDaoCache.getByNumeroCompte(4);

		// THEN
		Assert.assertNull(compteComptable);
	}

	@Test
	public void getListCompteComptable() {

		// WHEN
		List<CompteComptable> vList = compteComptableDaoCache.getListCompteComptable();

		// THEN
		Assert.assertNotNull(vList);
	}

	@Before
	public void initConsumer() {
		compteComptableDaoCache = new CompteComptableDaoCache();
		DaoProxy dao = mock(DaoProxy.class);
		when(dao.getComptabiliteDao()).thenReturn(new FakeComptabiblieDao());
		ConsumerHelper.configure(dao);
	}
}
