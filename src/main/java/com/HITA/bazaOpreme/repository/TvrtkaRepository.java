package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Tvrtka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvrtkaRepository extends JpaRepository<Tvrtka, Long> {
    // Custom query methods can be added here if needed
}

