package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Vrsta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VrstaRepository extends JpaRepository<Vrsta, Long> {
    // Custom query methods can be added here if needed
}

