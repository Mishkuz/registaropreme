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
import java.util.Optional;

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
    private String radnik;


    @Size(max = 512, message = "Opis održavanja ne smije biti duži od 512 znakova")
    private String opisOdrzavanja;

    @ManyToOne
    @JoinColumn(name = "oprema_id")
    private Oprema oprema;

    @ManyToOne
    @JoinColumn(name = "serviser_id")
    private Serviser serviser;

    @Column(name = "tip", nullable = true)
    private String tip;

    @Column(name = "datum_umjeravanja", nullable = true)
    private LocalDate datumUmjeravanja;

    @Column(name = "datum_otpreme",  nullable = true)
    private LocalDate datumOtpreme;

    @Column(name = "datum_povrata", nullable = true)
    private LocalDate datumPovrata;

    @Column(name = "datum_planiranog_servisiranja", nullable = true)
    private LocalDate datumPlaniranogServisiranja;

    @ManyToOne
    @JoinColumn(name="radiliste_id")
    Radiliste radiliste;



    public Odrzavanje(String radnik, String opisOdrzavanja,
                       LocalDate datumOtpreme, LocalDate datumPovrata, Radiliste radiliste, Oprema oprema) {
        this.radnik = radnik;
        this.opisOdrzavanja = opisOdrzavanja;
        this.datumOtpreme = datumOtpreme;
        this.datumPovrata = datumPovrata;
        this.radiliste = radiliste;
        this.oprema = oprema;
    }


    public Odrzavanje(String radnik,LocalDate datumUmjeravanja, Radiliste radiliste, Oprema oprema) {
        this.radnik = radnik;
        this.datumUmjeravanja = datumUmjeravanja;
        this.radiliste = radiliste;
        this.oprema = oprema;

    }


}

