package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vrsta")
@Data
@NoArgsConstructor
public class Vrsta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vrsta")
    private Long idVrsta;

    @ManyToOne
    @JoinColumn(name = "kategorija_id", referencedColumnName = "id_kategorija")
    private Kategorija kategorija;

    @NotBlank(message = "Šifra ne smije biti prazna")
    @Size(max = 20, message = "Šifra ne smije biti duža od 20 znakova")
    private String sifra;

    @NotBlank(message = "Naziv vrste ne smije biti prazan")
    @Size(max = 50, message = "Naziv vrste ne smije biti duži od 50 znakova")
    private String vrsta;

    public Vrsta(Long idVrsta, Kategorija kategorija, String sifra, String vrsta) {
        this.idVrsta = idVrsta;
        this.kategorija = kategorija;
        this.sifra = sifra;
        this.vrsta = vrsta;
    }
}
