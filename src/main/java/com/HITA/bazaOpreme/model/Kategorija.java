package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kategorija")
@NoArgsConstructor
@Data
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategorija")
    private Long idKategorija;

    @NotBlank(message = "Šifra ne smije biti prazna")
    @Size(max = 20, message = "Šifra ne smije biti duža od 20 znakova")
    private String sifra;

    @NotBlank(message = "Naziv kategorije ne smije biti prazan")
    @Size(max = 50, message = "Naziv kategorije ne smije biti duži od 50 znakova")
    private String kategorija;

    @ManyToOne
    @JoinColumn(name = "radiliste_id")
    private Radiliste radiliste ;


    public Kategorija(Long idKategorija, String sifra, String kategorija, Radiliste radiliste) {
        this.idKategorija = idKategorija;
        this.sifra = sifra;
        this.kategorija = kategorija;
        this.radiliste = radiliste;
    }

}

