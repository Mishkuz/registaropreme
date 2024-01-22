package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "oprema")
public class Oprema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oprema")
    private Long idOprema;

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
    @JoinColumn(name = "vrsta_id", referencedColumnName = "id_vrsta")
    private Vrsta vrsta;

    @ManyToOne
    @JoinColumn(name = "proizvodjac_id", referencedColumnName = "id_proizvodjac")
    private Proizvodjac proizvodjac;

    @ManyToOne
    @JoinColumn(name = "vlasnik_id", referencedColumnName = "id_vlasnik")
    private Vlasnik vlasnik;

    @ManyToOne
    @JoinColumn(name = "ups_id", referencedColumnName = "id_ups")
    private Ups ups;

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

    public Oprema(Long idOprema, String sifra, String naziv, String serijskiBroj, String inventarskiBroj, Vrsta vrsta, Proizvodjac proizvodjac, Vlasnik vlasnik, Ups ups, LocalDate godinaProizvodnje, LocalDate datumNabave, Boolean certifikat, Integer intervalServisiranjaUMjesecima, LocalDate datumPlaniranogServisiranja, Boolean ispravno, Boolean otpisano, LocalDate datumOtpisa) {
        this.idOprema = idOprema;
        this.sifra = sifra;
        this.naziv = naziv;
        this.serijskiBroj = serijskiBroj;
        this.inventarskiBroj = inventarskiBroj;
        this.vrsta = vrsta;
        this.proizvodjac = proizvodjac;
        this.vlasnik = vlasnik;
        this.ups = ups;
        this.godinaProizvodnje = godinaProizvodnje;
        this.datumNabave = datumNabave;
        this.certifikat = certifikat;
        this.intervalServisiranjaUMjesecima = intervalServisiranjaUMjesecima;
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
        this.ispravno = ispravno;
        this.otpisano = otpisano;
        this.datumOtpisa = datumOtpisa;
    }

    public Oprema() {
    }

    public Long getIdOprema() {
        return idOprema;
    }

    public void setIdOprema(Long idOprema) {
        this.idOprema = idOprema;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSerijskiBroj() {
        return serijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public String getInventarskiBroj() {
        return inventarskiBroj;
    }

    public void setInventarskiBroj(String inventarskiBroj) {
        this.inventarskiBroj = inventarskiBroj;
    }

    public Vrsta getVrsta() {
        return vrsta;
    }

    public void setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
    }

    public Proizvodjac getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(Proizvodjac proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public Vlasnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    public Ups getUps() {
        return ups;
    }

    public void setUps(Ups ups) {
        this.ups = ups;
    }

    public LocalDate getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public void setGodinaProizvodnje(LocalDate godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public LocalDate getDatumNabave() {
        return datumNabave;
    }

    public void setDatumNabave(LocalDate datumNabave) {
        this.datumNabave = datumNabave;
    }

    public Boolean getCertifikat() {
        return certifikat;
    }

    public void setCertifikat(Boolean certifikat) {
        this.certifikat = certifikat;
    }

    public Integer getIntervalServisiranjaUMjesecima() {
        return intervalServisiranjaUMjesecima;
    }

    public void setIntervalServisiranjaUMjesecima(Integer intervalServisiranjaUMjesecima) {
        this.intervalServisiranjaUMjesecima = intervalServisiranjaUMjesecima;
    }

    public LocalDate getDatumPlaniranogServisiranja() {
        return datumPlaniranogServisiranja;
    }

    public void setDatumPlaniranogServisiranja(LocalDate datumPlaniranogServisiranja) {
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
    }

    public Boolean getIspravno() {
        return ispravno;
    }

    public void setIspravno(Boolean ispravno) {
        this.ispravno = ispravno;
    }

    public Boolean getOtpisano() {
        return otpisano;
    }

    public void setOtpisano(Boolean otpisano) {
        this.otpisano = otpisano;
    }

    public LocalDate getDatumOtpisa() {
        return datumOtpisa;
    }

    public void setDatumOtpisa(LocalDate datumOtpisa) {
        this.datumOtpisa = datumOtpisa;
    }
    // Constructors, Getters, and Setters
}

