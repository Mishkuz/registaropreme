package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "oprema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @JoinColumn(name = "proizvodjac_id", referencedColumnName = "id")
    private Proizvodjac proizvodjac;

    @ManyToOne
    @JoinColumn(name = "vlasnik_id", referencedColumnName = "id")
    private Vlasnik vlasnik;

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

    @Column(name = "na_servisu", nullable = true)
    private boolean naServisu;

    @Column(name = "na_umjeravanju", nullable = true)
    private boolean naUmjeravanju;

    @Column(name = "ups", nullable = true)
    private String ups;

    @ManyToOne
    @JoinColumn(name = "radiliste_id")
    Radiliste radiliste;

    public Oprema(String sifra, String naziv, String serijskiBroj, String inventarskiBroj, Kategorija kategorija, Vrsta vrsta,
                  Proizvodjac proizvodjac, Vlasnik vlasnik, LocalDate godinaProizvodnje, LocalDate datumNabave,
                  Boolean certifikat, Integer intervalServisiranjaUMjesecima, LocalDate datumPlaniranogServisiranja, Boolean ispravno, Boolean otpisano,
                  LocalDate datumOtpisa, Radiliste radiliste) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.serijskiBroj = serijskiBroj;
        this.inventarskiBroj = inventarskiBroj;
        this.kategorija = kategorija;
        this.vrsta = vrsta;
        this.proizvodjac = proizvodjac;
        this.vlasnik = vlasnik;
        this.godinaProizvodnje = godinaProizvodnje;
        this.datumNabave = datumNabave;
        this.certifikat = certifikat;
        this.intervalServisiranjaUMjesecima = intervalServisiranjaUMjesecima;
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
        this.ispravno = ispravno;
        this.otpisano = otpisano;
        this.datumOtpisa = datumOtpisa;
        this.radiliste = radiliste;
    }
}

