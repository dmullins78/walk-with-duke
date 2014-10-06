package org.cijug.invoice;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class InvoiceRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Invoice save(Invoice invoice) {
		entityManager.persist(invoice);
		return invoice;
	}
	
	public Collection<Invoice> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Invoice e");
        return (Collection<Invoice>) query.getResultList();
	}

	public Invoice findByEmail(String email) {
		try {
			return entityManager.createNamedQuery(Invoice.FIND_BY_EMAIL, Invoice.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}


}
