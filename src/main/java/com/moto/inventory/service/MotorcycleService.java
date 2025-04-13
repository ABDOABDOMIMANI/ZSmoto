package com.moto.inventory.service;

import com.moto.inventory.model.Motorcycle;
import com.moto.inventory.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;

    @Autowired
    public MotorcycleService(MotorcycleRepository motorcycleRepository) {
        this.motorcycleRepository = motorcycleRepository;
    }

    public List<Motorcycle> findAll() {
        return motorcycleRepository.findAll();
    }

    public Optional<Motorcycle> findById(Long id) {
        return motorcycleRepository.findById(id);
    }

    public Motorcycle save(Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }
    
    @Transactional

    public void deleteById(Long id) {
        motorcycleRepository.deleteById(id);
    }
}