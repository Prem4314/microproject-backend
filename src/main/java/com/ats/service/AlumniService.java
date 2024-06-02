package com.ats.service;

import com.ats.model.Alumni;
import com.ats.repository.AlumniRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AlumniService {

    @Autowired
    private AlumniRepository alumniRepository;

    public List<Alumni> listAll() {
        return (List<Alumni>) alumniRepository.findAll();
    }

    public void save(Alumni alumni) {
        alumniRepository.save(alumni);
    }

    public Alumni get(Long id) {
        return alumniRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        alumniRepository.deleteById(id);
    }

    public Alumni findByUsername(String username) {
        return alumniRepository.findByUsername(username);
    }

    public boolean isUsernameExists(String username) {
        return alumniRepository.existsByUsername(username);
    }

    public boolean isEmailExists(String email) {
        return alumniRepository.existsByEmail(email);
    }
    
    public Alumni getAlumniById(Long alumniId) {
        return get(alumniId);
    }
}
