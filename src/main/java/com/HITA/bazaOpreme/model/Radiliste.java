package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Radiliste implements Serializable {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImeRadilista() {
        return imeRadilista;
    }

    public void setImeRadilista(String imeRadilista) {
        this.imeRadilista = imeRadilista;
    }

    public Ustanova getUstanova() {
        return ustanova;
    }

    public void setUstanova(Ustanova ustanova) {
        this.ustanova = ustanova;
    }
}
