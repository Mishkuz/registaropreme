package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Ups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpsRepository extends JpaRepository<Ups, Long> {
    // Custom query methods can be added here if needed
}

