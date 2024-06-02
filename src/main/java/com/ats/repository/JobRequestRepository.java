package com.ats.repository;

import com.ats.model.Alumni;
import com.ats.model.JobRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class JobRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public JobRequest save(JobRequest jobRequest) {
        if (jobRequest.getId() == null) {
            entityManager.persist(jobRequest);
            return jobRequest;
        } else {
            return entityManager.merge(jobRequest);
        }
    }

    public Optional<JobRequest> findById(Long id) {
        JobRequest jobRequest = entityManager.find(JobRequest.class, id);
        return jobRequest != null ? Optional.of(jobRequest) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(JobRequest jobRequest) {
        if (entityManager.contains(jobRequest)) {
            entityManager.remove(jobRequest);
        } else {
            entityManager.remove(entityManager.merge(jobRequest));
        }
    }

    public List<JobRequest> findAll() {
        String query = "SELECT jr FROM JobRequest jr";
        return entityManager.createQuery(query, JobRequest.class).getResultList();
    }

    public List<JobRequest> findByAlumni(Alumni alumni) {
        String query = "SELECT jr FROM JobRequest jr WHERE jr.alumni = :alumni";
        return entityManager.createQuery(query, JobRequest.class)
                            .setParameter("alumni", alumni)
                            .getResultList();
    }
}
