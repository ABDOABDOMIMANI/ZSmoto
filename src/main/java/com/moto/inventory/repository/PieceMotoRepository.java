package com.moto.inventory.repository;

import com.moto.inventory.model.PieceMoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceMotoRepository extends JpaRepository<PieceMoto, Long> {
}