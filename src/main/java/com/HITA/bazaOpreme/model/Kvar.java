package com.HITA.bazaOpreme.model;

import com.HITA.bazaOpreme.model.archive.Radnik;
import com.HITA.bazaOpreme.model.archive.Serviser;
import com.HITA.bazaOpreme.model.archive.TipServisa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "kvar")
public class Kvar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


  /*  // Assuming the other entities are already annotated with @Entity
    @ManyToOne
    @JoinColumn(name = "radnik_id", referencedColumnName = "id_radnik")
    private Radnik radnik;
   */

    @ManyToOne
    @JoinColumn(name = "oprema_id", referencedColumnName = "id")
    private Oprema oprema;

    @NotBlank(message = "Ime i prezime radnika")
    @Size(max = 100, message = "Ime i prezime trebaju biti 512 znakova")
    private String prijavioRadnik;

    @NotBlank(message = "Opis kvara ne smije biti prazan")
    @Size(max = 512, message = "Opis kvara ne smije biti duži od 512 znakova")
    private String opisKvara;

    @NotNull(message = "Datum prijave je obavezan")
    @Column(name = "datum_prijave")
    private LocalDate datumPrijave;

}

