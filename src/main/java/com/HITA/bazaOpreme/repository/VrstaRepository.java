package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Vrsta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VrstaRepository extends JpaRepository<Vrsta, Long> {
    // Custom query methods can be added here if needed
    @Override
    List<Vrsta> findAll();
}

