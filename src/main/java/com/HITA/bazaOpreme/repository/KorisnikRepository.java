package com.HITA.bazaOpreme.repository;

import com.HITA.bazaOpreme.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Korisnik findByEmailAndPassword(String email, String password);
}
