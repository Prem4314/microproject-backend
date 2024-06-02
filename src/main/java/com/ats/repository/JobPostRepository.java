package com.ats.repository;

import com.ats.model.Alumni;
import com.ats.model.JobPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class JobPostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public JobPost save(JobPost jobPost) {
        if (jobPost.getId() == null) {
            entityManager.persist(jobPost);
            return jobPost;
        } else {
            return entityManager.merge(jobPost);
        }
    }

    public Optional<JobPost> findById(Long id) {
        JobPost jobPost = entityManager.find(JobPost.class, id);
        return jobPost != null ? Optional.of(jobPost) : Optional.empty();
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    @Transactional
    public void delete(JobPost jobPost) {
        if (entityManager.contains(jobPost)) {
            entityManager.remove(jobPost);
        } else {
            entityManager.remove(entityManager.merge(jobPost));
        }
    }

    public List<JobPost> findAll() {
        String query = "SELECT jp FROM JobPost jp";
        return entityManager.createQuery(query, JobPost.class).getResultList();
    }

    public List<JobPost> findByAlumni(Alumni alumni) {
        String query = "SELECT jp FROM JobPost jp WHERE jp.alumni = :alumni";
        return entityManager.createQuery(query, JobPost.class)
                            .setParameter("alumni", alumni)
                            .getResultList();
    }
}
