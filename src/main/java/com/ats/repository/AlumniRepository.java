package com.ats.repository;

import com.ats.model.Alumni;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class AlumniRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Alumni save(Alumni alumni) {
        if (alumni.getId() == null) {
            entityManager.persist(alumni);
            return alumni;
        } else {
            return entityManager.merge(alumni);
        }
    }

    public Optional<Alumni> findById(Long id) {
        Alumni alumni = entityManager.find(Alumni.class, id);
        return alumni != null ? Optional.of(alumni) : Optional.empty();
    }

    public Alumni findByUsername(String username) {
        String query = "SELECT a FROM Alumni a WHERE a.username = :username";
        return entityManager.createQuery(query, Alumni.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public boolean existsByUsername(String username) {
        String query = "SELECT COUNT(a) FROM Alumni a WHERE a.username = :username";
        Long count = entityManager.createQuery(query, Long.class)
                                  .setParameter("username", username)
                                  .getSingleResult();
        return count > 0;
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(a) FROM Alumni a WHERE a.email = :email";
        Long count = entityManager.createQuery(query, Long.class)
                                  .setParameter("email", email)
                                  .getSingleResult();
        return count > 0;
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(Alumni alumni) {
        if (entityManager.contains(alumni)) {
            entityManager.remove(alumni);
        } else {
            entityManager.remove(entityManager.merge(alumni));
        }
    }

    public List<Alumni> findAll() {
        String query = "SELECT a FROM Alumni a";
        return entityManager.createQuery(query, Alumni.class).getResultList();
    }
}
