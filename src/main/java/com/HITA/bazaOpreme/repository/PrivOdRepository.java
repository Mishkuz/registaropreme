package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.PrivOd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PrivOdRepository extends JpaRepository<PrivOd,Long> {


    @Transactional
    @Modifying
    @Query("delete from PrivOd p where p.oprema = ?1")
    int deleteByOprema(Oprema oprema);

    void deleteByOprema(Optional<Oprema> byId);

    PrivOd findByOprema_Id(Long id);
}
