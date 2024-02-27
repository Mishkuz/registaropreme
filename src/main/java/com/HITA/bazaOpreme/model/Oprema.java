package com.HITA.bazaOpreme.model;

import com.HITA.bazaOpreme.model.archive.Proizvodjac;
import com.HITA.bazaOpreme.model.archive.Ups;
import com.HITA.bazaOpreme.model.archive.Vlasnik;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "oprema")
@Data @NoArgsConstructor @AllArgsConstructor
public class Oprema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Šifra ne smije biti prazna")
    @Size(max = 20, message = "Šifra ne smije biti duža od 20 znakova")
    private String sifra;

    @NotBlank(message = "Naziv ne smije biti prazan")
    @Size(max = 100, message = "Naziv ne smije biti duži od 100 znakova")
    private String naziv;

    @Size(max = 20, message = "Serijski broj ne smije biti duži od 20 znakova")
    private String serijskiBroj;

    @Size(max = 20, message = "Inventarski broj ne smije biti duži od 20 znakova")
    private String inventarskiBroj;

    @ManyToOne
    @JoinColumn(name = "kategorija_id", referencedColumnName = "id_kategorija")
    private Kategorija kategorija;

    @ManyToOne
    @JoinColumn(name = "vrsta_id", referencedColumnName = "id_vrsta")
    private Vrsta vrsta;

    @ManyToOne
    @JoinColumn(name = "tvrtka_proizvodjac_id", referencedColumnName = "id")
    private Tvrtka tvrtkaProizvodjac;

    @ManyToOne
    @JoinColumn(name = "tvrtka_vlasnik_id", referencedColumnName = "id")
    private Tvrtka tvrtkaVlasnik;

    @Column(name = "godina_proizvodnje")
    private LocalDate godinaProizvodnje;

    @Column(name = "datum_nabave")
    private LocalDate datumNabave;

    private Boolean certifikat;

    @Column(name = "interval_servisiranja_u_mjesecima")
    private Integer intervalServisiranjaUMjesecima;

    @Column(name = "datum_planiranog_servisiranja")
    private LocalDate datumPlaniranogServisiranja;

    private Boolean ispravno;

    private Boolean otpisano;

    @Column(name = "datum_otpisa")
    private LocalDate datumOtpisa;

}

