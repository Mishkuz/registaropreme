package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "odrzavanje")
public class Odrzavanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odrzavanje")
    private Long idOdrzavanje;

    // Assuming the other entities are already annotated with @Entity
    @ManyToOne
    @JoinColumn(name = "radnik_id", referencedColumnName = "id_radnik")
    private Radnik radnik;

    @ManyToOne
    @JoinColumn(name = "oprema_id", referencedColumnName = "id_oprema")
    private Oprema oprema;

    @ManyToOne
    @JoinColumn(name = "tip_servisa_id", referencedColumnName = "id_tip_servisa")
    private TipServisa tipServisa;

    @ManyToOne
    @JoinColumn(name = "serviser_id", referencedColumnName = "id_serviser")
    private Serviser serviser;

    @NotBlank(message = "Opis kvara ne smije biti prazan")
    @Size(max = 512, message = "Opis kvara ne smije biti duži od 512 znakova")
    private String opisKvara;

    @NotNull(message = "Datum prijave je obavezan")
    @Column(name = "datum_prijave")
    private LocalDate datumPrijave;

    @Column(name = "datum_otpreme")
    private LocalDate datumOtpreme;

    @Column(name = "datum_povrata")
    private LocalDate datumPovrata;

    @Column(name = "datum_planiranog_servisiranja")
    private LocalDate datumPlaniranogServisiranja;

    public Odrzavanje(Long idOdrzavanje, Radnik radnik, Oprema oprema, TipServisa tipServisa, Serviser serviser, String opisKvara, LocalDate datumPrijave, LocalDate datumOtpreme, LocalDate datumPovrata, LocalDate datumPlaniranogServisiranja) {
        this.idOdrzavanje = idOdrzavanje;
        this.radnik = radnik;
        this.oprema = oprema;
        this.tipServisa = tipServisa;
        this.serviser = serviser;
        this.opisKvara = opisKvara;
        this.datumPrijave = datumPrijave;
        this.datumOtpreme = datumOtpreme;
        this.datumPovrata = datumPovrata;
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
    }

    public Odrzavanje() {
    }

    public Long getIdOdrzavanje() {
        return idOdrzavanje;
    }

    public void setIdOdrzavanje(Long idOdrzavanje) {
        this.idOdrzavanje = idOdrzavanje;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public Oprema getOprema() {
        return oprema;
    }

    public void setOprema(Oprema oprema) {
        this.oprema = oprema;
    }

    public TipServisa getTipServisa() {
        return tipServisa;
    }

    public void setTipServisa(TipServisa tipServisa) {
        this.tipServisa = tipServisa;
    }

    public Serviser getServiser() {
        return serviser;
    }

    public void setServiser(Serviser serviser) {
        this.serviser = serviser;
    }

    public String getOpisKvara() {
        return opisKvara;
    }

    public void setOpisKvara(String opisKvara) {
        this.opisKvara = opisKvara;
    }

    public LocalDate getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(LocalDate datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    public LocalDate getDatumOtpreme() {
        return datumOtpreme;
    }

    public void setDatumOtpreme(LocalDate datumOtpreme) {
        this.datumOtpreme = datumOtpreme;
    }

    public LocalDate getDatumPovrata() {
        return datumPovrata;
    }

    public void setDatumPovrata(LocalDate datumPovrata) {
        this.datumPovrata = datumPovrata;
    }

    public LocalDate getDatumPlaniranogServisiranja() {
        return datumPlaniranogServisiranja;
    }

    public void setDatumPlaniranogServisiranja(LocalDate datumPlaniranogServisiranja) {
        this.datumPlaniranogServisiranja = datumPlaniranogServisiranja;
    }
    // Constructors, getters, and setters
}

