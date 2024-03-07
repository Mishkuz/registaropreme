package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Radiliste;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OpremaRepository extends JpaRepository<Oprema, Long> {
    // Custom query methods can be added here if needed


    @Override
    List<Oprema> findAll();

    List<Oprema> findByRadiliste(Radiliste radiliste);

    @Transactional
    @Modifying
    @Query("update Oprema o set o.naServisu = ?1 where o.id = ?2")
    void updateNaServisuById(boolean naServisu, Long id);

    List<Oprema> findByRadilisteAndNaServisu(Radiliste radiliste, boolean naServisu);

    @Transactional
    @Modifying
    @Query("update Oprema o set o.ispravno = ?1 where o.id = ?2")
    int updateIspravnoById(Boolean ispravno, Long id);

    @Transactional
    @Modifying
    @Query("update Oprema o set o.datumPlaniranogServisiranja = ?1 where o.id = ?2")
    int updateDatumPlaniranogServisiranjaById(LocalDate datumPlaniranogServisiranja, Long id);
}

