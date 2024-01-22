package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Kategorija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
    // Additional query methods can be defined here
}

