package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "radnik")
public class Radnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_radnik")
    private Long idRadnik;

    @NotBlank(message = "Šifra ne smije biti prazna")
    @Size(max = 20, message = "Šifra ne smije biti duža od 20 znakova")
    private String sifra;

    @NotBlank(message = "Ime ne smije biti prazno")
    @Size(max = 50, message = "Ime ne smije biti duže od 50 znakova")
    private String ime;

    @NotBlank(message = "Prezime ne smije biti prazno")
    @Size(max = 50, message = "Prezime ne smije biti duže od 50 znakova")
    private String prezime;

    public Radnik(Long idRadnik, String sifra, String ime, String prezime) {
        this.idRadnik = idRadnik;
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Radnik() {
    }

    public Long getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(Long idRadnik) {
        this.idRadnik = idRadnik;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
}

