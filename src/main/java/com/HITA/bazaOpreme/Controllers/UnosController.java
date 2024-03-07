package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Proizvodjac;
import com.HITA.bazaOpreme.model.Serviser;
import com.HITA.bazaOpreme.model.Vlasnik;
import com.HITA.bazaOpreme.repository.ServiserRepository;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UnosController {


    private final OpremaRepository opremaRepository;
    private final KvarRepository kvarRepository;
    private final KategorijaRepository kategorijaRepository;
    private final OdrzavanjeRepository odrzavanjeRepository;
    private final VrstaRepository vrstaRepository;
    private final  ServiserRepository serviserRepository;
    private VlasnikRepository vlasnikRepository;
    private final ProizvodjacRepository proizvodjacRepository;


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
                          @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session, @AuthenticationPrincipal UserDetails userDetails) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Serviser serviser = new Serviser(sifra, naziv, adresa, telefon, email, kOsoba,user.getRadiliste());
        serviserRepository.save(serviser);
        return "redirect:/pocetna";
    }
    @GetMapping("/spremiNovogProizvodaca")
    public String spremiP(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session, @AuthenticationPrincipal UserDetails userDetails) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Proizvodjac proizvodjac = new Proizvodjac(sifra, naziv, adresa, telefon, email, kOsoba,user.getRadiliste());
        proizvodjacRepository.save(proizvodjac);
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
