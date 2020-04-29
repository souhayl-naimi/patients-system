package io.naimi.patientsystem.DAO;

import io.naimi.patientsystem.Entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    public Page<Patient> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
