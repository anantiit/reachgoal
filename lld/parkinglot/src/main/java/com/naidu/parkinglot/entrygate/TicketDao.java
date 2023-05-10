package com.naidu.parkinglot.entrygate;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TicketDao {
	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public void save(Ticket ticket) {
		entityManager.persist(ticket);
	}

	@Transactional
	public void update(Ticket ticket) {
		entityManager.merge(ticket);

	}
}
