package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Radiliste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    String imeRadilista;

    @ManyToOne
    @JoinColumn(name = "ustanova_id")
    Ustanova ustanova;

    public Radiliste(String imeRadilista, Ustanova ustanova) {
        this.imeRadilista = imeRadilista;
        this.ustanova = ustanova;

    }
}
