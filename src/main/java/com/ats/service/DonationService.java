package com.ats.service;

import com.ats.model.Donation;
import com.ats.repository.DonationRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import java.util.List;

@Service
@Transactional
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    public List<Donation> listAll() {
        return (List<Donation>) donationRepository.findAll();
    }

    public void save(Donation donation) {
        donationRepository.save(donation);
    }

    public Donation get(Long id) {
        return donationRepository.findById(id).get();
    }

    public void delete(Long id) {
        donationRepository.deleteById(id);
    }
}
