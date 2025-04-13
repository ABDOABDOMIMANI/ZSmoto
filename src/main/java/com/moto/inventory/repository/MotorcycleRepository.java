package com.moto.inventory.repository;

import com.moto.inventory.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, String> {


    Optional<Motorcycle> findById(Long id);

    void deleteById(Long id);
}