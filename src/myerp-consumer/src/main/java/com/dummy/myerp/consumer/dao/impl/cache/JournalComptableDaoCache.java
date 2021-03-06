package com.dummy.myerp.consumer.dao.impl.cache;

import java.util.List;

import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;

/**
 * Cache DAO de {@link JournalComptable}
 */
public class JournalComptableDaoCache {

	// ==================== Attributs ====================
	/** The List compte comptable. */
	private List<JournalComptable> listJournalComptable;

	// ==================== Constructeurs ====================
	/**
	 * Instantiates a new Compte comptable dao cache.
	 */
	public JournalComptableDaoCache() {
	}

	// ==================== Méthodes ====================
	/**
	 * Gets by code.
	 *
	 * @param pCode le code du {@link JournalComptable}
	 * @return {@link JournalComptable} ou {@code null}
	 */

	public JournalComptable getByCodeJournal(String pCode) {
		if (listJournalComptable == null) {
			listJournalComptable = getListJournalComptable();
		}
		return JournalComptable.getByCode(listJournalComptable, pCode);
	}

	public JournalComptable getByCode(String pCode) {
		if (listJournalComptable == null) {
			listJournalComptable = getListJournalComptable();
		}
		JournalComptable vRetour = getByCodeJournal(pCode);
		return vRetour;
	}

	public List<JournalComptable> getListJournalComptable() {
		return ConsumerHelper.getDaoProxy().getComptabiliteDao().getListJournalComptable();
	}
}
