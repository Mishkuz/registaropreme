package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    String ime;
    String prezime;

    @Email(message = "Unesite ispravnu e-mail adresu")
    private String email;

    String password;
    @ManyToOne
    @JoinColumn(name="radiliste_id")
    Radiliste radiliste;

    public Korisnik(String ime, String prezime, String email, String password, Radiliste radiliste) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.password = password;
        this.radiliste = radiliste;
    }
}
