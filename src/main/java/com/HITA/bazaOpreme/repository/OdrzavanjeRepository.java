package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Odrzavanje;
import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.archive.Odrzavanja_Old;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdrzavanjeRepository extends JpaRepository<Odrzavanje, Long> {
     // Custom query methods can be added here if needed


     List<Odrzavanje> findAll();

}


