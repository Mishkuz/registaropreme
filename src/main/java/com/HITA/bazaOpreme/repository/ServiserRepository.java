package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Serviser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiserRepository extends JpaRepository<Serviser, Long> {
    // Custom query methods can be added here if needed
}

