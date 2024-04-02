package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.ServiserRepository;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String unosP(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_proizvodjaca";
    }
    @GetMapping("/unosNovogServisera")
    public String unosNS(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_novog_servisera";
    }
    @GetMapping("/spremiNovogServisera")
    public String spremiS(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session,
                          @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Serviser serviser = new Serviser(sifra, naziv, adresa, telefon, email, kOsoba,user.getRadiliste());
        serviserRepository.save(serviser);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }
    @GetMapping("/spremiNovogProizvodjaca")
    public String spremiP(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Proizvodjac proizvodjac = new Proizvodjac(sifra, naziv, adresa, telefon, email, kOsoba,user.getRadiliste());
        proizvodjacRepository.save(proizvodjac);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }

    @GetMapping("/unosNovogVlasnika")
    public String unosNV(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_novog_vlasnika";
    }

    @GetMapping("/spremiNovogVlasnika")
    public String spremiNV(@RequestParam(name = "sifra") String sifra, @RequestParam(name = "naziv") String naziv,
                           @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                           @RequestParam(name = "email") String email, @RequestParam(name = "kOsoba") String kOsoba, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Vlasnik vlasnik = new Vlasnik(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        vlasnikRepository.save(vlasnik);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }

    @GetMapping("/spremiNovuKategoriju")
    public String spremiNovuKategoriju(@RequestParam("sifra") String sifra,
                                       @RequestParam("naziv") String naziv,
                                       HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        if (user == null) {
            return "redirect:/login";
        }

        Kategorija novaKategorija = new Kategorija();
        novaKategorija.setSifra(sifra);
        novaKategorija.setKategorija(naziv);
        novaKategorija.setRadiliste(user.getRadiliste());

        kategorijaRepository.save(novaKategorija);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }

    @GetMapping("/unosNoveKategorije")
    public String unosKategorije(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_kategorije";
    }

    @GetMapping("/unosNoveVrste")
    public String unosVrste(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute("kategorije", kategorije);
        model.addAttribute("user", user);
        return "z-unos_vrste";
    }

    @GetMapping("/spremiNovuVrstu")
    public String unosNoveVrste(@RequestParam("sifra") String sifra,
                                @RequestParam("naziv") String naziv,
                                @RequestParam("kategorijaId") Long kategorijaId,
                                HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        if (user == null) {
            return "redirect:/login";
        }
        Vrsta novaVrsta = new Vrsta();
        novaVrsta.setSifra(sifra);
        novaVrsta.setVrsta(naziv);
        novaVrsta.setRadiliste(user.getRadiliste());
        novaVrsta.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        vrstaRepository.save(novaVrsta);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }

}
