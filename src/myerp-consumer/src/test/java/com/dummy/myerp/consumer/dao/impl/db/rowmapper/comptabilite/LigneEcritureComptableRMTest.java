package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

public class LigneEcritureComptableRMTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void mapRow() {

		LigneEcritureComptableRM rm = new LigneEcritureComptableRM();

		final LigneEcritureComptable result = new LigneEcritureComptable();

		final JdbcTemplate template = Mockito.mock(JdbcTemplate.class);

		Mockito.when(template.queryForObject(Mockito.anyString(), Mockito.any(RowMapper.class)))
				.thenAnswer((Answer<LigneEcritureComptable>) invocation -> {

					ResultSet rs = Mockito.mock(ResultSet.class);
					String libelle = "libelle";
					BigDecimal debit = new BigDecimal(110.00);
					BigDecimal credit = new BigDecimal(110.00);
					Integer compteNumero = new Integer(401);

					Mockito.when(rs.getString("libelle")).thenReturn(libelle);
					Mockito.when(rs.getBigDecimal("credit")).thenReturn(credit);
					Mockito.when(rs.getBigDecimal("debit")).thenReturn(debit);
					Mockito.when(rs.getObject("compte_comptable_numero", Integer.class)).thenReturn(compteNumero);

					LigneEcritureComptable actual = rm.mapRow(rs, 0);

					result.setCredit(actual.getCredit());
					result.setDebit(actual.getDebit());
					result.setLibelle(actual.getLibelle());
					result.setCompteComptable(actual.getCompteComptable());

					return result;
				});

		LigneEcritureComptable ligneEcritureComptable = template
				.queryForObject("SELECT * FROM ligne_ecriture_comptable WHERE id = -3", rm);

		Assert.assertEquals(ligneEcritureComptable.getLibelle(), "libelle");
		Assert.assertEquals(ligneEcritureComptable.getCredit(), new BigDecimal(110.00));
		Assert.assertEquals(ligneEcritureComptable.getDebit(), new BigDecimal(110.00));
	}
}
