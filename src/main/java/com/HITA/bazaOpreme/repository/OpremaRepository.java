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

    List<Oprema> findByRadilisteAndOtpisanoIsNull(Radiliste radiliste);
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

    List<Oprema> findByOtpisano(boolean otpisano);

    List<Oprema> findByRadilisteAndIspravnoFalse(Radiliste radiliste);

    @Transactional
    @Modifying
    @Query("update Oprema o set o.naUmjeravanju = ?1 where o.id = ?2")
    int updateNaUmjeravanjuById(boolean naUmjeravanju, Long id);

    List<Oprema> findByRadilisteAndNaUmjeravanju(Radiliste radiliste, boolean naUmjeravanju);


    List<Oprema>  findByRadilisteAndOtpisanoAndNaServisuAndNaUmjeravanjuAndIspravno(Radiliste radiliste, Boolean otpisano, boolean naServisu, boolean naUmjeravanju, Boolean ispravno);

    @Query("""
            select o from Oprema o
            where o.radiliste = ?1 and o.ispravno = ?2 and o.naServisu = ?3 and o.naUmjeravanju = ?4 and o.otpisano = ?5""")
    List<Oprema> findByRadilisteAndIspravnoAndNaServisuAndNaUmjeravanjuAndOtpisano(Radiliste radiliste, Boolean ispravno, boolean naServisu, boolean naUmjeravanju, Boolean otpisano);

    @Transactional
    @Modifying
    @Query("update Oprema o set o.certifikat = ?1 where o.id = ?2")
    int updateCertifikatById(Boolean certifikat, Long id);
}

