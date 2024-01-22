package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Radnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadnikRepository extends JpaRepository<Radnik, Long> {
    // Custom query methods can be added here if needed
}

