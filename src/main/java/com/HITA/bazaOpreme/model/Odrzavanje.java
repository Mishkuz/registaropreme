package com.HITA.bazaOpreme.model;

import com.HITA.bazaOpreme.model.archive.Radnik;
import com.HITA.bazaOpreme.model.archive.TipServisa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "odrzavanje")
@Data
@NoArgsConstructor
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
    private String opisOdrzavanja;

    @ManyToOne
    @JoinColumn(name = "oprema_id")
    private Oprema oprema;

    @ManyToOne
    @JoinColumn(name = "serviser_id")
    private Serviser serviser;

    private boolean izvanredan;

    private boolean umjeravanje;

    @NotNull(message = "Datum prijave je obavezan")
    @Column(name = "datum_prijave")
    private Date datumPrijave;

    @Column(name = "datum_otpreme")
    private Date datumOtpreme;

    @Column(name = "datum_povrata")
    private Date datumPovrata;

    @ManyToOne
    @JoinColumn(name="radiliste_id")
    Radiliste radiliste;



    public Odrzavanje(String prijavioRadnik, String opisOdrzavanja, boolean izvanredan, boolean umjeravanje,
                      Date datumPrijave, Date datumOtpreme, Date datumPovrata, Radiliste radiliste ) {
        this.prijavioRadnik = prijavioRadnik;
        this.opisOdrzavanja = opisOdrzavanja;
        this.izvanredan = izvanredan;
        this.umjeravanje = umjeravanje;
        this.datumPrijave = datumPrijave;
        this.datumOtpreme = datumOtpreme;
        this.datumPovrata = datumPovrata;
        this.radiliste = radiliste;
    }

}

