package com.moto.inventory.controller;

import com.moto.inventory.model.Motorcycle;
import com.moto.inventory.service.MotorcycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/motorcycles")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @Autowired
    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @GetMapping
    public ResponseEntity<List<Motorcycle>> getAllMotorcycles() {
        List<Motorcycle> motorcycles = motorcycleService.findAll();
        return new ResponseEntity<>(motorcycles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycleById(@PathVariable Long id) {
        Optional<Motorcycle> motorcycle = motorcycleService.findById(id);
        return motorcycle.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Motorcycle> createMotorcycle(@Valid @RequestBody Motorcycle motorcycle) {
        Motorcycle savedMotorcycle = motorcycleService.save(motorcycle);
        return new ResponseEntity<>(savedMotorcycle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable Long id, @Valid @RequestBody Motorcycle motorcycle) {
        if (!motorcycleService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        motorcycle.setId(id);
        Motorcycle updatedMotorcycle = motorcycleService.save(motorcycle);
        return new ResponseEntity<>(updatedMotorcycle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable Long id) {
        if (!motorcycleService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        motorcycleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}