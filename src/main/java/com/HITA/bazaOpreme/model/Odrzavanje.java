package com.HITA.bazaOpreme.model;

import com.HITA.bazaOpreme.model.archive.Radnik;
import com.HITA.bazaOpreme.model.archive.Serviser;
import com.HITA.bazaOpreme.model.archive.TipServisa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "odrzavanje")
public class Odrzavanje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
    // Assuming the other entities are already annotated with @Entity
    @ManyToOne
    @JoinColumn(name = "radnik_id", referencedColumnName = "id_radnik")
    private Radnik radnik;
    */


    @NotBlank(message = "Ime i prezime radnika treba biti difiniran")
    @Size(max = 100, message = "Ime i prezime trebaju biti 512 znakova")
    private String prijavioRadnik;


    @Size(max = 512, message = "Opis održavanja ne smije biti duži od 512 znakova")
    private String opisOdržavanja;

    @ManyToOne
    @JoinColumn(name = "oprema_id")
    private Oprema oprema;

    @ManyToOne
    @JoinColumn(name = "tvrtka_serviser_id")
    private Tvrtka tvrtkaServiser;

    private boolean izvanredan;

    private boolean umjeravanje;

    @NotNull(message = "Datum prijave je obavezan")
    @Column(name = "datum_prijave")
    private LocalDate datumPrijave;

    @Column(name = "datum_otpreme")
    private LocalDate datumOtpreme;

    @Column(name = "datum_povrata")
    private LocalDate datumPovrata;

    @Column(name = "datum_planiranog_servisiranja")
    private LocalDate datumPlaniranogServisiranja;


}

