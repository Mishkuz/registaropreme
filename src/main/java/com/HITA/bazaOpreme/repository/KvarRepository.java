package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Kvar;
import com.HITA.bazaOpreme.model.Odrzavanje;
import com.HITA.bazaOpreme.model.Radiliste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KvarRepository extends JpaRepository<Kvar, Long> {
    // Custom query methods can be added here if needed


    List<Kvar> findAll();

    List<Kvar> findByRadiliste(Radiliste radiliste);
}