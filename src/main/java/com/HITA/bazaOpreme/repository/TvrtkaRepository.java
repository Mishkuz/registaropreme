package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Kategorija;
import com.HITA.bazaOpreme.model.Tvrtka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvrtkaRepository extends JpaRepository<Tvrtka, Long> {
    // Custom query methods can be added here if needed

    @Override
    List<Tvrtka> findAll();
}

