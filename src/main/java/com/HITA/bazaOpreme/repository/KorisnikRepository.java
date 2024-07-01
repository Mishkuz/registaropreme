package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Korisnik findByEmailAndPassword(String email, String password);

    Korisnik findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Korisnik k set k.role = ?1")
    int updateRoleBy(Role role);
}
