package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ustanova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    String imeUstanove;

    public Ustanova(String imeUstanove) {
        this.imeUstanove = imeUstanove;
    }
}
