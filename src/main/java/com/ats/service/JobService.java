package com.ats.service;

import com.ats.model.JobPost;
import com.ats.model.JobRequest;
import com.ats.repository.JobPostRepository;
import com.ats.repository.JobRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class JobService {

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobRequestRepository jobRequestRepository;

    public void saveJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
    }

    public List<JobPost> listAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public JobPost getJobPost(Long id) {
        return jobPostRepository.findById(id).orElse(null);
    }

    public void saveJobRequest(JobRequest jobRequest) {
        jobRequestRepository.save(jobRequest);
    }

    public List<JobRequest> listAllJobRequests() {
        return jobRequestRepository.findAll();
    }

    public JobRequest getJobRequest(Long id) {
        return jobRequestRepository.findById(id).orElse(null);
    }
}
