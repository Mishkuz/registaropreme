package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "kategorija")
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategorija")
    private Long idKategorija;

    @NotBlank(message = "Naziv kategorije ne smije biti prazan")
    @Size(max = 50, message = "Naziv kategorije ne smije biti duži od 50 znakova")
    private String kategorija;

    public Kategorija(Long idKategorija, String kategorija) {
        this.idKategorija = idKategorija;
        this.kategorija = kategorija;
    }

    public Kategorija() {
    }

    public Long getIdKategorija() {
        return idKategorija;
    }

    public void setIdKategorija(Long idKategorija) {
        this.idKategorija = idKategorija;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }
}

