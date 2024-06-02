package com.ats.repository;

import com.ats.model.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class AdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Admin save(Admin admin) {
        if (admin.getId() == null) {
            entityManager.persist(admin);
            return admin;
        } else {
            return entityManager.merge(admin);
        }
    }

    public Optional<Admin> findById(Long id) {
        Admin admin = entityManager.find(Admin.class, id);
        return admin != null ? Optional.of(admin) : Optional.empty();
    }

    public Admin findByUsername(String username) {
        String query = "SELECT a FROM Admin a WHERE a.username = :username";
        return entityManager.createQuery(query, Admin.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Transactional
    public void delete(Admin admin) {
        if (entityManager.contains(admin)) {
            entityManager.remove(admin);
        } else {
            entityManager.remove(entityManager.merge(admin));
        }
    }
}
