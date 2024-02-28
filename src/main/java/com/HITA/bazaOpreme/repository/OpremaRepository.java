package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Radiliste;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpremaRepository extends JpaRepository<Oprema, Long> {
    // Custom query methods can be added here if needed


    @Override
    List<Oprema> findAll();

    List<Oprema> findByRadiliste(Radiliste radiliste);
}

