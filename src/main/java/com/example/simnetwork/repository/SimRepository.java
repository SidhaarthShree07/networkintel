package com.example.simnetwork.repository;

import com.example.simnetwork.model.SimRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// public interface SimRepository extends JpaRepository<SimRecord, Long> {
//     Optional<SimRecord> findByCity(String city); // Safer than returning null
// }

public interface SimRepository extends JpaRepository<SimRecord, Long> {
    SimRecord findByCity(String city); // No Optional, just return SimRecord
}
