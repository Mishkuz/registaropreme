package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Oprema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpremaRepository extends JpaRepository<Oprema, Long> {
    // Custom query methods can be added here if needed
}

