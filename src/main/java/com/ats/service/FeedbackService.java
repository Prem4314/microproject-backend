// FeedbackService.java
package com.ats.service;

import com.ats.model.Feedback;
import com.ats.repository.FeedbackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> listAll() {
        return feedbackRepository.findAll();
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Feedback get(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }
}
