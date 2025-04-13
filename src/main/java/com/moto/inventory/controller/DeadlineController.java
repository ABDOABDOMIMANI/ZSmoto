package com.moto.inventory.controller;

import com.moto.inventory.model.Deadline;
import com.moto.inventory.service.DeadlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deadlines")
public class DeadlineController {

    private final DeadlineService deadlineService;

    @Autowired
    public DeadlineController(DeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    @GetMapping
    public ResponseEntity<List<Deadline>> getAllDeadlines() {
        List<Deadline> deadlines = deadlineService.findAll();
        return new ResponseEntity<>(deadlines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deadline> getDeadlineById(@PathVariable Long id) {
        Optional<Deadline> deadline = deadlineService.findById(id);
        return deadline.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Deadline> createDeadline(@Valid @RequestBody Deadline deadline) {
        Deadline savedDeadline = deadlineService.save(deadline);
        return new ResponseEntity<>(savedDeadline, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deadline> updateDeadline(@PathVariable Long id, @Valid @RequestBody Deadline deadline) {
        if (!deadlineService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        deadline.setId(id);
        Deadline updatedDeadline = deadlineService.save(deadline);
        return new ResponseEntity<>(updatedDeadline, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeadline(@PathVariable Long id) {
        if (!deadlineService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        deadlineService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}