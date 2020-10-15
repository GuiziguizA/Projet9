package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.db.fake.FakeComptabiblieDao;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

public class EcritureComptableRMTest {

	EcritureComptableRM rm = new EcritureComptableRM();

	@Test
	public void mapRow() {

		final EcritureComptable result = new EcritureComptable();
		ConsumerHelper.configure(() -> new FakeComptabiblieDao());
		final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

		Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
				.thenAnswer((Answer<EcritureComptable>) invocation -> {

					ResultSet rs = Mockito.mock(ResultSet.class);

					Integer id = new Integer(4);
					String libelle = "libelle";
					String reference = "BQ-2019/00001";
					JournalComptable journal = new JournalComptable("AC", "Achat");

					Mockito.when(rs.getString("libelle")).thenReturn(libelle);
					Mockito.when(rs.getString("reference")).thenReturn(reference);
					Mockito.when(rs.getString("journal_code")).thenReturn(journal.getCode());
					Mockito.when(rs.getInt("id")).thenReturn(id);

					EcritureComptable actual = rm.mapRow(rs, 0);

					result.setId(actual.getId());
					result.setReference(actual.getReference());
					result.setLibelle(actual.getLibelle());
					result.setDate(actual.getDate());
					result.setJournal(actual.getJournal());

					return result;
				});

		EcritureComptable ecritureComptable = template.queryForObject("SELECT * FROM ecriture_comptable WHERE id = 4",
				rm);

		Assert.assertEquals(ecritureComptable.getLibelle(), "libelle");
		Assert.assertEquals(ecritureComptable.getId(), new Integer(4));
		Assert.assertEquals(ecritureComptable.getReference(), "BQ-2019/00001");
		Assert.assertEquals(ecritureComptable.getJournal().toString(), new JournalComptable("AC", "Achat").toString());
	}

	@Test
	public void getJournalCode() throws SQLException {
		ConsumerHelper.configure(() -> new FakeComptabiblieDao());
		ResultSet rs = Mockito.mock(ResultSet.class);
		Mockito.when(rs.getString(Mockito.anyString())).thenReturn("AC");
		JournalComptable journalComptable = rm.getJournalCode(rs);

		// THEN
		Assert.assertNotNull(journalComptable);
	}

	@Test
	public void loadListLigneEcriture() {
		EcritureComptable ecritureComptable = new EcritureComptable();
		rm.loadListLigneEcriture(ecritureComptable);
		Assert.assertEquals(2, ecritureComptable.getListLigneEcriture().size());
	}
}
