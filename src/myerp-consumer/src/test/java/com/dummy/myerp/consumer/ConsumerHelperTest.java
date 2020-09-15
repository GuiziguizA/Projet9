package com.dummy.myerp.consumer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;

public class ConsumerHelperTest extends ConsumerHelper {

	@Test
	public void getDaoProxyTest() {
		configure(mock(DaoProxy.class));
		DaoProxy dao = getDaoProxy();
		assertNotNull(dao);
	}

}
