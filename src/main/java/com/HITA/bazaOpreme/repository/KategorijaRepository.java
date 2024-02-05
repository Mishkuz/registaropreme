package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Kategorija;
import com.HITA.bazaOpreme.model.Oprema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategorijaRepository extends JpaRepository<Kategorija, Long> {
    // Additional query methods can be defined here

    Kategorija save(Kategorija kategorija);
    @Override
    List<Kategorija> findAll();
}

