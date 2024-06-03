package com.ats.junittest;



import com.ats.model.Alumni;
import com.ats.repository.AlumniRepository;
import com.ats.service.AlumniService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlumniServiceTest {

    @Mock
    private AlumniRepository alumniRepository;

    @InjectMocks
    private AlumniService alumniService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAll() {
        Alumni alumni1 = new Alumni();
        alumni1.setId(1L);
        Alumni alumni2 = new Alumni();
        alumni2.setId(2L);
        List<Alumni> alumniList = Arrays.asList(alumni1, alumni2);

        when(alumniRepository.findAll()).thenReturn(alumniList);

        List<Alumni> result = alumniService.listAll();

        assertEquals(2, result.size());
        verify(alumniRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);

        alumniService.save(alumni);

        verify(alumniRepository, times(1)).save(alumni);
    }

    @Test
    public void testGet() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);
        when(alumniRepository.findById(1L)).thenReturn(Optional.of(alumni));

        Alumni result = alumniService.get(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(alumniRepository, times(1)).findById(1L);
    }

    @Test
    public void testDelete() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);

        alumniService.delete(1L);

        verify(alumniRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByUsername() {
        Alumni alumni = new Alumni();
        alumni.setUsername("john_doe");
        when(alumniRepository.findByUsername("john_doe")).thenReturn(alumni);

        Alumni result = alumniService.findByUsername("john_doe");

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(alumniRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    public void testIsUsernameExists() {
        when(alumniRepository.existsByUsername("john_doe")).thenReturn(true);

        boolean result = alumniService.isUsernameExists("john_doe");

        assertTrue(result);
        verify(alumniRepository, times(1)).existsByUsername("john_doe");
    }

    @Test
    public void testIsEmailExists() {
        when(alumniRepository.existsByEmail("john_doe@example.com")).thenReturn(true);

        boolean result = alumniService.isEmailExists("john_doe@example.com");

        assertTrue(result);
        verify(alumniRepository, times(1)).existsByEmail("john_doe@example.com");
    }

    @Test
    public void testGetAlumniById() {
        Alumni alumni = new Alumni();
        alumni.setId(1L);
        when(alumniRepository.findById(1L)).thenReturn(Optional.of(alumni));

        Alumni result = alumniService.getAlumniById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(alumniRepository, times(1)).findById(1L);
    }
}

