package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;

public class BusinessProxyImplTest extends BusinessProxyImpl {

	@Test
	public void getComptabiliteManagerTest() {
		ComptabiliteManager manager = getComptabiliteManager();
		assertNotNull(manager);
	}

}
