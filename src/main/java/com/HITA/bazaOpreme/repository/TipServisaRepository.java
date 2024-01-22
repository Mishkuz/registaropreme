package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.TipServisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipServisaRepository extends JpaRepository<TipServisa, Long> {
    // Custom query methods can be added here if needed
}

