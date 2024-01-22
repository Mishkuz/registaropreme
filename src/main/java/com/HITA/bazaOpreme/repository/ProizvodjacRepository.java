package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Proizvodjac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProizvodjacRepository extends JpaRepository<Proizvodjac, Long> {
    // Custom query methods can be added here if needed
}

