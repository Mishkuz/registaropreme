package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VlasnikRepository extends JpaRepository<Vlasnik, Long> {
    // Custom query methods can be added here if needed
}

