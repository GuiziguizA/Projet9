package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

public class JournalComptableRMTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mapRow() {
		JournalComptableRM journal = new JournalComptableRM();
		JournalComptable jn = new JournalComptable();

		final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

		Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
				.thenAnswer((Answer<JournalComptable>) invocation -> {
					String code = "BQ";
					String libelle = "Banque";
					ResultSet pRS = Mockito.mock(ResultSet.class);
					Mockito.when(pRS.getString("code")).thenReturn(code);
					Mockito.when(pRS.getString("libelle")).thenReturn(libelle);

					JournalComptable actual = journal.mapRow(pRS, 0);

					jn.setCode(actual.getCode());
					jn.setLibelle(actual.getLibelle());

					return jn;
				});

		JournalComptable journalComptable = template
				.queryForObject("SELECT * FROM myerp.journal_comptable WHERE id = -3", journal);
		Assert.assertEquals(journalComptable.getCode(), "BQ");
		Assert.assertEquals(journalComptable.getLibelle(), "Banque");
		Assert.assertNotEquals(journalComptable.getCode(), "AC");
		Assert.assertNotEquals(journalComptable.getLibelle(), "Achat");
	}
}