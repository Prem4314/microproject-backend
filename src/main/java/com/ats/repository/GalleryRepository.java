package com.ats.repository;

import com.ats.model.Gallery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class GalleryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Gallery save(Gallery gallery) {
        if (gallery.getId() == null) {
            entityManager.persist(gallery);
            return gallery;
        } else {
            return entityManager.merge(gallery);
        }
    }

    public Optional<Gallery> findById(Long id) {
        Gallery gallery = entityManager.find(Gallery.class, id);
        return gallery != null ? Optional.of(gallery) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(Gallery gallery) {
        if (entityManager.contains(gallery)) {
            entityManager.remove(gallery);
        } else {
            entityManager.remove(entityManager.merge(gallery));
        }
    }

    public List<Gallery> findAll() {
        String query = "SELECT g FROM Gallery g";
        return entityManager.createQuery(query, Gallery.class).getResultList();
    }
}
