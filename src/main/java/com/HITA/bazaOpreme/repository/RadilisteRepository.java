package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Proizvodjac;
import com.HITA.bazaOpreme.model.Radiliste;
import com.HITA.bazaOpreme.model.Ustanova;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadilisteRepository extends JpaRepository<Radiliste, Long> {
    List<Radiliste> findByUstanova(Ustanova ustanova);
}
