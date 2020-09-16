package com.dummy.myerp.consumer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeDataSource;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;

public class AbrstactDbConsumerTest extends AbstractDbConsumer {

	@Test
	public void getDaoProxyTest() {

		// GIVEN
		ConsumerHelper.configure(mock(DaoProxy.class));

		// WHEN
		DaoProxy dao = getDaoProxy();

		// THEN
		assertNotNull(dao);
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void getDataSourceTest() {

		// GIVEN
		Map<DataSourcesEnum, DataSource> mapDataSource = mock(Map.class);
		Mockito.when(mapDataSource.get(Mockito.anyObject())).thenReturn(new FakeDataSource());
		configure(mapDataSource);

		// WHEN
		DataSource data = getDataSource(DataSourcesEnum.MYERP);

		// THEN
		assertNotNull(data);
	}

	@Test(expected = UnsatisfiedLinkError.class)
	public void getDataSourceTestNull() {

		// GIVEN
		Map<DataSourcesEnum, DataSource> mapDataSource = mock(Map.class);
		Mockito.when(mapDataSource.get(Mockito.anyObject())).thenReturn(null);
		configure(mapDataSource);

		// WHEN
		DataSource data = getDataSource(DataSourcesEnum.MYERP);
	}

	@Test
	public void queryGetSequenceValuePostgreSQLTest() {

		JdbcTemplate template = mock(JdbcTemplate.class);
		Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(null);

		Assert.assertNull(queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "test", Integer.class, template));
	}

	@Test
	public void testConstructeur() {
		AbstractDbConsumer abstractDbConsumer = new AbstractDbConsumer() {
		};
		assertNotNull(abstractDbConsumer);
	}
}
