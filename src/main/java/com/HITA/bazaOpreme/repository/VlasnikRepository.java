package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Radiliste;
import com.HITA.bazaOpreme.model.Vlasnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VlasnikRepository extends JpaRepository<Vlasnik, Long> {

    List<Vlasnik> findByRadiliste(Radiliste radiliste);
}