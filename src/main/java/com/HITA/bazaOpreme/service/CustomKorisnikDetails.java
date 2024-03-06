package com.HITA.bazaOpreme.service;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomKorisnikDetails implements UserDetails {

  private Korisnik korisnik;


    public CustomKorisnikDetails(Korisnik korisnik){
        this.korisnik = korisnik;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = korisnik.getRole().name();
                String authority = "ROLE_" + roleName;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));
        return authorities;
    }

    @Override
    public String getPassword() {
        return korisnik.getPassword();
    }

    @Override
    public String getUsername() {
        return korisnik.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
