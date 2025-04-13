package com.moto.inventory.service;

import com.moto.inventory.model.PieceMoto;
import com.moto.inventory.repository.PieceMotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PieceMotoService {

    private final PieceMotoRepository pieceMotoRepository;

    @Autowired
    public PieceMotoService(PieceMotoRepository pieceMotoRepository) {
        this.pieceMotoRepository = pieceMotoRepository;
    }

    public List<PieceMoto> findAll() {
        return pieceMotoRepository.findAll();
    }

    public Optional<PieceMoto> findById(Long id) {
        return pieceMotoRepository.findById(id);
    }

    public PieceMoto save(PieceMoto pieceMoto) {
        return pieceMotoRepository.save(pieceMoto);
    }

    public void deleteById(Long id) {
        pieceMotoRepository.deleteById(id);
    }
}