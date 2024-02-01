package com.HITA.bazaOpreme.model.archive;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

//@Entity
@Table(name = "tip_servisa")
public class TipServisa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tip_servisa")
    private Long idTipServisa;

    @NotBlank(message = "Tip servisa ne smije biti prazan")
    @Size(max = 50, message = "Tip servisa ne smije biti duži od 50 znakova")
    private String tipServisa;

    public TipServisa(Long idTipServisa, String tipServisa) {
        this.idTipServisa = idTipServisa;
        this.tipServisa = tipServisa;
    }

    public TipServisa() {
    }

    public Long getIdTipServisa() {
        return idTipServisa;
    }

    public void setIdTipServisa(Long idTipServisa) {
        this.idTipServisa = idTipServisa;
    }

    public String getTipServisa() {
        return tipServisa;
    }

    public void setTipServisa(String tipServisa) {
        this.tipServisa = tipServisa;
    }
}

