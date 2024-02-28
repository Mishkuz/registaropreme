package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Proizvodjac;
import com.HITA.bazaOpreme.model.Radiliste;
import com.HITA.bazaOpreme.model.Serviser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProizvodjacRepository extends JpaRepository<Proizvodjac, Long> {


    List<Proizvodjac> findByRadiliste(Radiliste radiliste);
}