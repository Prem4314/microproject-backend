package com.ats.service;

import com.ats.model.Admin;
import com.ats.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public boolean validateAdmin(String username, String password) {
        Admin admin = findByUsername(username);
        return admin != null && admin.getPassword().equals(password);
    }

    public Admin get(Long adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    public void delete(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        if (admin != null) {
            adminRepository.delete(admin);
        }
    }
}