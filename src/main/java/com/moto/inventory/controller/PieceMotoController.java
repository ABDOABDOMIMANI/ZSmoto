package com.moto.inventory.controller;

import com.moto.inventory.model.PieceMoto;
import com.moto.inventory.service.PieceMotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pieces")
public class PieceMotoController {

    private final PieceMotoService pieceMotoService;

    @Autowired
    public PieceMotoController(PieceMotoService pieceMotoService) {
        this.pieceMotoService = pieceMotoService;
    }

    @GetMapping
    public ResponseEntity<List<PieceMoto>> getAllPieces() {
        List<PieceMoto> pieces = pieceMotoService.findAll();
        return new ResponseEntity<>(pieces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceMoto> getPieceById(@PathVariable Long id) {
        Optional<PieceMoto> piece = pieceMotoService.findById(id);
        return piece.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<PieceMoto> createPiece(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "purchasePrice", required = false) java.math.BigDecimal purchasePrice,
            @RequestParam(value = "sellPrice", required = false) java.math.BigDecimal sellPrice,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        String imageBase64 = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PieceMoto piece = new PieceMoto();
        piece.setName(name);
        piece.setDescription(description);
        piece.setPurchasePrice(purchasePrice);
        piece.setSellPrice(sellPrice);
        piece.setQuantity(quantity);
        piece.setImage(imageBase64);

        PieceMoto savedPiece = pieceMotoService.save(piece);
        return new ResponseEntity<>(savedPiece, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<PieceMoto> updatePiece(@PathVariable Long id, @Valid @RequestBody PieceMoto piece) {
        if (!pieceMotoService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        piece.setId(id);
        PieceMoto updatedPiece = pieceMotoService.save(piece);
        return new ResponseEntity<>(updatedPiece, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PieceMoto> updatePieceMultipart(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "purchasePrice", required = false) java.math.BigDecimal purchasePrice,
            @RequestParam(value = "sellPrice", required = false) java.math.BigDecimal sellPrice,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        java.util.Optional<PieceMoto> existingOpt = pieceMotoService.findById(id);
        if (existingOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PieceMoto existing = existingOpt.get();
        if (name != null) existing.setName(name);
        if (description != null) existing.setDescription(description);
        if (purchasePrice != null) existing.setPurchasePrice(purchasePrice);
        if (sellPrice != null) existing.setSellPrice(sellPrice);
        if (quantity != null) existing.setQuantity(quantity);
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                existing.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        PieceMoto updatedPiece = pieceMotoService.save(existing);
        return new ResponseEntity<>(updatedPiece, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePiece(@PathVariable Long id) {
        if (!pieceMotoService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pieceMotoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}