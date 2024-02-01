package com.HITA.bazaOpreme.model.archive;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

//@Entity
@Table(name = "ups")
public class Ups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ups")
    private Long idUps;

    @NotBlank(message = "Status ne smije biti prazan")
    @Size(max = 50, message = "Status ne smije biti duži od 50 znakova")
    private String status;

    public Ups(Long idUps, String status) {
        this.idUps = idUps;
        this.status = status;
    }

    public Ups() {
    }

    public Long getIdUps() {
        return idUps;
    }

    public void setIdUps(Long idUps) {
        this.idUps = idUps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

