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

    public Long getId() {
        return id;
    }

    public String getSifra() {
        return sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public void setInventarskiBroj(String inventarskiBroj) {
        this.inventarskiBroj = inventarskiBroj;
    }

    public void setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
    }

    public void setTvrtkaProizvodjac(Tvrtka tvrtkaProizvodjac) {
        this.tvrtkaProizvodjac = tvrtkaProizvodjac;
    }

    public void setTvrtkaVlasnik(Tvrtka tvrtkaVlasnik) {
        this.tvrtkaVlasnik = tvrtkaVlasnik;
    }

    public void setGodinaProizvodnje(LocalDate godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public void setDatumNabave(LocalDate datumNabave) {
        this.datumNabave = datumNabave;
    }

    public void setCertifikat(Boolean certifikat) {
        this.certifikat = certifikat;
    }

    public void setIntervalServisiranjaUMjesecima(Integer intervalServisiranjaUMjesecima) {
        this.intervalServisiranjaUMjesecima = intervalServisiranjaUMjesecima;
    }

    public void setDatumPlaniranogServisiranja(LocalDate datumPlaniranogServisiranja) {
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
    }

    public void setIspravno(Boolean ispravno) {
        this.ispravno = ispravno;
    }

    public void setOtpisano(Boolean otpisano) {
        this.otpisano = otpisano;
    }

    public void setDatumOtpisa(LocalDate datumOtpisa) {
        this.datumOtpisa = datumOtpisa;
    }

    public String getSerijskiBroj() {
        return serijskiBroj;
    }

    public String getInventarskiBroj() {
        return inventarskiBroj;
    }

    public Vrsta getVrsta() {
        return vrsta;
    }

    public Tvrtka getTvrtkaProizvodjac() {
        return tvrtkaProizvodjac;
    }

    public Tvrtka getTvrtkaVlasnik() {
        return tvrtkaVlasnik;
    }

    public LocalDate getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public LocalDate getDatumNabave() {
        return datumNabave;
    }

    public Boolean getCertifikat() {
        return certifikat;
    }

    public Integer getIntervalServisiranjaUMjesecima() {
        return intervalServisiranjaUMjesecima;
    }

    public LocalDate getDatumPlaniranogServisiranja() {
        return datumPlaniranogServisiranja;
    }

    public Boolean getIspravno() {
        return ispravno;
    }

    public Boolean getOtpisano() {
        return otpisano;
    }

    public LocalDate getDatumOtpisa() {
        return datumOtpisa;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

