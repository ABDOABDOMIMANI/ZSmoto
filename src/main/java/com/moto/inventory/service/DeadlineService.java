package com.moto.inventory.service;

import com.moto.inventory.model.Deadline;
import com.moto.inventory.repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeadlineService {

    private final DeadlineRepository deadlineRepository;

    @Autowired
    public DeadlineService(DeadlineRepository deadlineRepository) {
        this.deadlineRepository = deadlineRepository;
    }

    public List<Deadline> findAll() {
        return deadlineRepository.findAll();
    }

    public Optional<Deadline> findById(Long id) {
        return deadlineRepository.findById(id);
    }

    public Deadline save(Deadline deadline) {
        
        return deadlineRepository.save(deadline);
    }

    public void deleteById(Long id) {
        deadlineRepository.deleteById(id);
    }
}