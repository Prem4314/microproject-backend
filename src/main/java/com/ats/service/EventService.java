package com.ats.service;

import com.ats.model.Event;
import com.ats.repository.EventRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    public void save(Event event) {
        eventRepository.save(event);
    }

    public Event get(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
