package com.HITA.bazaOpreme.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "kvar")
public class Kvar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /*  // Assuming the other entities are already annotated with @Entity
      @ManyToOne
      @JoinColumn(name = “radnik_id”, referencedColumnName = “id_radnik”)
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
    //@NotNull(message = “Datum prijave je obavezan”)
    @Column(name = "datum_prijave")
    private Date datumPrijave;
    public Kvar(String prijavioRadnik, String opisKvara, Date datumPrijave) {
        this.oprema = oprema;
        this.prijavioRadnik = prijavioRadnik;
        this.opisKvara = opisKvara;
        this.datumPrijave = datumPrijave;
    }
    public Kvar() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Oprema getOprema() {
        return oprema;
    }
    public void setOprema(Oprema oprema) {
        this.oprema = oprema;
    }
    public String getPrijavioRadnik() {
        return prijavioRadnik;
    }
    public void setPrijavioRadnik(String prijavioRadnik) {
        this.prijavioRadnik = prijavioRadnik;
    }
    public String getOpisKvara() {
        return opisKvara;
    }
    public void setOpisKvara(String opisKvara) {
        this.opisKvara = opisKvara;
    }
    public Date getDatumPrijave() {
        return datumPrijave;
    }
    public void setDatumPrijave(Date datumPrijave) {
        this.datumPrijave = datumPrijave;
    }
}

