package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Serviser;
import com.HITA.bazaOpreme.model.Vlasnik;
import com.HITA.bazaOpreme.repository.ServiserRepository;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UnosController {
    @Autowired
    OpremaRepository opremaRepository;
    @Autowired
    KvarRepository kvarRepository;
    @Autowired
    KategorijaRepository kategorijaRepository;
    @Autowired
    OdrzavanjeRepository odrzavanjeRepository;
    @Autowired
    VrstaRepository vrstaRepository;
    @Autowired
    private ServiserRepository serviserRepository;
    @Autowired
    private VlasnikRepository vlasnikRepository;


    @GetMapping("/unosProizvodjaca")
    public String unosP() {
        return "z-unos_proizvođaca.html";
    }

    @GetMapping("/unosNovogServisera")
    public String unosNS() {
        return "z-unos_novog_servisera.html";
    }

    @GetMapping("/spremiNovogServisera")
    public String spremiS(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Serviser serviser = new Serviser(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        serviserRepository.save(serviser);
        return "redirect:/pocetna";
    }


    @GetMapping("/unosNovogVlasnika")
    public String unosNV() {
        return "z-unos_novog_vlasnika.html";
    }


    @GetMapping("/spremiNovogVlasnika")
    public String spremiNV(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                           @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                           @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Vlasnik vlasnik = new Vlasnik(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        vlasnikRepository.save(vlasnik);
        return "redirect:/pocetna";
    }
}
