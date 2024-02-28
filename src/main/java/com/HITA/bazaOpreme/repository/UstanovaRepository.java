package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Proizvodjac;
import com.HITA.bazaOpreme.model.Ustanova;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UstanovaRepository extends JpaRepository<Ustanova, Long> {
}
