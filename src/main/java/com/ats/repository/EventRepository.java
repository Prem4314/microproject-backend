package com.ats.repository;

import com.ats.model.Event;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Event save(Event event) {
        if (event.getId() == null) {
            entityManager.persist(event);
            return event;
        } else {
            return entityManager.merge(event);
        }
    }

    public Optional<Event> findById(Long id) {
        Event event = entityManager.find(Event.class, id);
        return event != null ? Optional.of(event) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(Event event) {
        if (entityManager.contains(event)) {
            entityManager.remove(event);
        } else {
            entityManager.remove(entityManager.merge(event));
        }
    }

    public List<Event> findAll() {
        String query = "SELECT e FROM Event e";
        return entityManager.createQuery(query, Event.class).getResultList();
    }
}
