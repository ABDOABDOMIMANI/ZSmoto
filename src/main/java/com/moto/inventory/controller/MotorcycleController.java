package com.moto.inventory.controller;

import com.moto.inventory.model.Motorcycle;
import com.moto.inventory.service.MotorcycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
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

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Motorcycle> createMotorcycle(
            @RequestParam(value = "numChassis", required = false) String numChassis,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "cylinderSize", required = false) Integer cylinderSize,
            @RequestParam(value = "isNew", required = false) Boolean isNew,
            @RequestParam(value = "mileageKm", required = false) Integer mileageKm,
            @RequestParam(value = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(value = "sellPrice", required = false) BigDecimal sellPrice,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        String imageBase64 = null;

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setNumChassis(numChassis);
        motorcycle.setModel(model);
        motorcycle.setBrand(brand);
        motorcycle.setCylinderSize(cylinderSize);
        motorcycle.setIsNew(isNew);
        motorcycle.setMileageKm(mileageKm);
        motorcycle.setPurchasePrice(purchasePrice);
        motorcycle.setSellPrice(sellPrice);
        motorcycle.setQuantity(quantity);
        motorcycle.setImage(imageBase64);

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

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Motorcycle> updateMotorcycleMultipart(
            @PathVariable Long id,
            @RequestParam(value = "numChassis", required = false) String numChassis,
            @RequestParam(value = "model", required = false) String model,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "cylinderSize", required = false) Integer cylinderSize,
            @RequestParam(value = "isNew", required = false) Boolean isNew,
            @RequestParam(value = "mileageKm", required = false) Integer mileageKm,
            @RequestParam(value = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(value = "sellPrice", required = false) BigDecimal sellPrice,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        Optional<Motorcycle> existingOpt = motorcycleService.findById(id);
        if (existingOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Motorcycle existing = existingOpt.get();

        if (numChassis != null) existing.setNumChassis(numChassis);
        if (model != null) existing.setModel(model);
        if (brand != null) existing.setBrand(brand);
        if (cylinderSize != null) existing.setCylinderSize(cylinderSize);
        if (isNew != null) existing.setIsNew(isNew);
        if (mileageKm != null) existing.setMileageKm(mileageKm);
        if (purchasePrice != null) existing.setPurchasePrice(purchasePrice);
        if (sellPrice != null) existing.setSellPrice(sellPrice);
        if (quantity != null) existing.setQuantity(quantity);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                existing.setImage(imageBase64);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Motorcycle updated = motorcycleService.save(existing);
        return new ResponseEntity<>(updated, HttpStatus.OK);
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