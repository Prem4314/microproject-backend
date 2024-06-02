package com.ats.repository;

import com.ats.model.Donation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class DonationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Donation save(Donation donation) {
        if (donation.getId() == null) {
            entityManager.persist(donation);
            return donation;
        } else {
            return entityManager.merge(donation);
        }
    }

    public Optional<Donation> findById(Long id) {
        Donation donation = entityManager.find(Donation.class, id);
        return donation != null ? Optional.of(donation) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(Donation donation) {
        if (entityManager.contains(donation)) {
            entityManager.remove(donation);
        } else {
            entityManager.remove(entityManager.merge(donation));
        }
    }

    public List<Donation> findAll() {
        String query = "SELECT d FROM Donation d";
        return entityManager.createQuery(query, Donation.class).getResultList();
    }
}
