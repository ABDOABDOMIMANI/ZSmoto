package com.moto.inventory.controller;

import com.moto.inventory.model.PieceMoto;
import com.moto.inventory.service.PieceMotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<PieceMoto> createPiece(@Valid @RequestBody PieceMoto piece) {
        PieceMoto savedPiece = pieceMotoService.save(piece);
        return new ResponseEntity<>(savedPiece, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceMoto> updatePiece(@PathVariable Long id, @Valid @RequestBody PieceMoto piece) {
        if (!pieceMotoService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        piece.setId(id);
        PieceMoto updatedPiece = pieceMotoService.save(piece);
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