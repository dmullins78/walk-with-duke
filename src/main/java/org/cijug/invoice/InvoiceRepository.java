package org.cijug.invoice;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Collection;

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

	public Invoice findByName(String name) {
		try {
			return entityManager.createNamedQuery(Invoice.FIND_BY_NAME, Invoice.class)
					.setParameter("email", name)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

    public Invoice findById(Long id) {
        return entityManager.find(Invoice.class, id);
    }
}
