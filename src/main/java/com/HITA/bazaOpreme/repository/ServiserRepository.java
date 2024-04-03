package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Radiliste;
import com.HITA.bazaOpreme.model.Serviser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiserRepository extends JpaRepository<Serviser, Long> {


    List<Serviser> findByRadiliste(Radiliste radiliste);

}