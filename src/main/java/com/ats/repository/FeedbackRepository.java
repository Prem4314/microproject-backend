package com.ats.repository;

import com.ats.model.Feedback;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class FeedbackRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Feedback save(Feedback feedback) {
        if (feedback.getId() == null) {
            entityManager.persist(feedback);
            return feedback;
        } else {
            return entityManager.merge(feedback);
        }
    }

    public Optional<Feedback> findById(Long id) {
        Feedback feedback = entityManager.find(Feedback.class, id);
        return feedback != null ? Optional.of(feedback) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(Feedback feedback) {
        if (entityManager.contains(feedback)) {
            entityManager.remove(feedback);
        } else {
            entityManager.remove(entityManager.merge(feedback));
        }
    }

    public List<Feedback> findAll() {
        String query = "SELECT f FROM Feedback f";
        return entityManager.createQuery(query, Feedback.class).getResultList();
    }
}
