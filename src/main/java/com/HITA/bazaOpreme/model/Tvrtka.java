package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tvrtka")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tvrtka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Šifra ne smije biti prazna")
    @Size(max = 20, message = "Šifra ne smije biti duža od 20 znakova")
    private String sifra;

    @NotBlank(message = "Naziv ne smije biti prazan")
    @Size(max = 50, message = "Naziv ne smije biti duži od 50 znakova")
    private String naziv;

    @Size(max = 50, message = "Adresa ne smije biti duža od 50 znakova")
    private String adresa;

    @Size(max = 20, message = "Telefon ne smije biti duži od 20 znakova")
    private String telefon;

    @Email(message = "Neispravan format e-maila")
    @Size(max = 50, message = "Email ne smije biti duži od 50 znakova")
    private String email;

    @Size(max = 50, message = "Kontakt osoba ne smije biti duža od 50 znakova")
    @Column(name = "kontakt_osoba")
    private String kontaktOsoba;

}
