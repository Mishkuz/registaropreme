package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Odrzavanje;
import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Radiliste;
import com.HITA.bazaOpreme.model.archive.Odrzavanja_Old;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdrzavanjeRepository extends JpaRepository<Odrzavanje, Long> {
     // Custom query methods can be added here if needed


     List<Odrzavanje> findAll();

     List<Odrzavanje> findByRadiliste(Radiliste radiliste);

     List<Odrzavanje> findByTip(String tip);

     List<Odrzavanje> findByTipOrTip(String tip, String tip1);

     List<Odrzavanje> findByRadilisteAndTip(Radiliste radiliste, String tip);

     List<Odrzavanje> findByRadilisteAndTipOrTip(Radiliste radiliste, String tip, String tip1);
}


