package com.HITA.bazaOpreme.service;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomKorisnikDetailsService implements UserDetailsService {


    public CustomKorisnikDetailsService(KorisnikRepository repo) {
        this.repo = repo;
    }

    private final KorisnikRepository repo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik korisnik = repo.findByEmail(email);
        if (korisnik == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomKorisnikDetails(korisnik);
    }

/*
    public Collection<? extends GrantedAuthority> authorities(){
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }*/
}
