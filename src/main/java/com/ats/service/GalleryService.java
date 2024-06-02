package com.ats.service;

import com.ats.model.Gallery;
import com.ats.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    public List<Gallery> listAll() {
        return galleryRepository.findAll();
    }

    public void save(Gallery gallery) {
        galleryRepository.save(gallery);
    }

    public Gallery get(Long id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        galleryRepository.deleteById(id);
    }
}
