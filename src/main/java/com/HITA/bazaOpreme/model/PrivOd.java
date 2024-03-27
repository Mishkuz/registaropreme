package com.HITA.bazaOpreme.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "privOd")
@Getter
@Setter
@NoArgsConstructor

public class PrivOd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "oprema_id", unique = true, nullable = true)
    private Oprema oprema;

    @ManyToOne
    @JoinColumn(name = "serviser_id", nullable = true)
    private Serviser serviser;

    @Column(name = "datum_otpreme", nullable = true)
    private LocalDate datumOtpreme;
}
