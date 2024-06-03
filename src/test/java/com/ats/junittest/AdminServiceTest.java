package com.ats.junittest;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ats.model.Admin;
import com.ats.repository.AdminRepository;
import com.ats.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateAdmin_ValidCredentials() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");

        when(adminRepository.findByUsername("admin")).thenReturn(admin);

        assertTrue(adminService.validateAdmin("admin", "password"));
    }

    @Test
    public void testValidateAdmin_InvalidCredentials() {
        when(adminRepository.findByUsername("admin")).thenReturn(null);

        assertFalse(adminService.validateAdmin("admin", "password"));
    }

    @Test
    public void testSaveAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");

        when(adminRepository.save(admin)).thenReturn(admin);

        Admin savedAdmin = adminService.save(admin);
        assertEquals("admin", savedAdmin.getUsername());
    }

    @Test
    public void testFindById() {
        Admin admin = new Admin();
        admin.setId(1L);

        when(adminRepository.findById(1L)).thenReturn(java.util.Optional.of(admin));

        Admin foundAdmin = adminService.findById(1L);
        assertNotNull(foundAdmin);
        assertEquals(1L, foundAdmin.getId());
    }
}
