package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.ServiserRepository;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UnosController {


    private final OpremaRepository opremaRepository;
    private final KvarRepository kvarRepository;
    private final KategorijaRepository kategorijaRepository;
    private final OdrzavanjeRepository odrzavanjeRepository;
    private final VrstaRepository vrstaRepository;
    private final ServiserRepository serviserRepository;
    private VlasnikRepository vlasnikRepository;
    private final ProizvodjacRepository proizvodjacRepository;


    @GetMapping("/unosNovogServisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String unosNS(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_novog_servisera";
    }



    @GetMapping("/spremiNovogServisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiS(@RequestParam(name = "sifra", required = false) String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email", required = false) String email, @RequestParam(name = "kOsoba", required = false) String kOsoba, HttpSession session,
                          @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Serviser serviser = new Serviser(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        serviserRepository.save(serviser);
        model.addAttribute("user", user);
        return "redirect:/popis_servisera";
    }



    @GetMapping("/unosProizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String unosP(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_proizvodjaca";
    }



    @GetMapping("/spremiNovogProizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiP(@RequestParam(name = "sifra", required = false) String sifra, @RequestParam(name = "naziv") String naziv,
                          @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                          @RequestParam(name = "email", required = false) String email, @RequestParam(name = "kOsoba", required = false) String kOsoba, HttpSession session, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Proizvodjac proizvodjac = new Proizvodjac(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        proizvodjacRepository.save(proizvodjac);
        model.addAttribute("user", user);
        return "redirect:/popis_proizvodjaca";
    }




    @GetMapping("/unosNovogVlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String unosNV(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_novog_vlasnika";
    }



    @GetMapping("/spremiNovogVlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiNV(@RequestParam(name = "sifra", required = false) String sifra, @RequestParam(name = "naziv") String naziv,
                           @RequestParam(name = "adresa") String adresa, @RequestParam(name = "telefon") String telefon,
                           @RequestParam(name = "email", required = false) String email, @RequestParam(name = "kOsoba", required = false) String kOsoba, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Vlasnik vlasnik = new Vlasnik(sifra, naziv, adresa, telefon, email, kOsoba, user.getRadiliste());
        vlasnikRepository.save(vlasnik);
        model.addAttribute("user", user);
        return "redirect:/popis_vlasnika";
    }



    @GetMapping("/spremiNovuKategoriju")
    @PreAuthorize("hasRole('ROLE_USER')")
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
        return "redirect:/popis_kategorija";
    }



    @GetMapping("/unosNoveKategorije")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String unosKategorije(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        model.addAttribute("user", user);
        return "z-unos_kategorije";
    }


    @GetMapping("/unosNoveVrste")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String unosVrste(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute("kategorije", kategorije);
        model.addAttribute("user", user);
        return "z-unos_vrste";
    }


    @GetMapping("/spremiNovuVrstu")
    @PreAuthorize("hasRole('ROLE_USER')")
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
        return "redirect:/popis_vrsta";
    }



    @GetMapping("/z-unos_novog_kvara")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String zunos_novog_kvara(Model model, @RequestParam(name = "opremaId") Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Kvar> kvarList = kvarRepository.findByRadiliste(user.getRadiliste());
        Optional<Oprema> oprema = opremaRepository.findById(opremaId);

        if (oprema.isPresent()) {
            model.addAttribute("oprema", oprema.get());
        } else {
            // Ovdje obradite slučaj kada oprema s traženim ID-em nije pronađena
        }

        model.addAttribute("kvarList", kvarList);
        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-prijava_kvara.html";
    }



    @GetMapping("/z-spremiuredaj")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String zspremiUredaj(@RequestParam("sifra") String sifra,
                                @RequestParam("naziv") String naziv,
                                @RequestParam("serijskiBroj") String serijskiBroj,
                                @RequestParam(name = "inventarskiBroj", required = false) String inventarskiBroj,
                                @RequestParam("kategorijaId") Long kategorijaId,
                                @RequestParam("vrstaId") Long vrstaId,
                                @RequestParam("proizvodjacId") Long proizvodjacId,
                                @RequestParam(name = "godinaProizvodnje", required = false) LocalDate godinaProizvodnje,
                                @RequestParam(name = "datumNabave", required = false) LocalDate datumNabave,
                                @RequestParam("certifikat") boolean certifikat,
                                @RequestParam("vlasnikId") Long vlasnikId,
                                @RequestParam("ups") String ups,
                                @RequestParam("intervalServisiranjaUMjesecima") Integer intervalServisiranjaUMjesecima,
                                @RequestParam("datumPlaniranogServisiranja") LocalDate datumPlaniranogServisiranja,
                                @RequestParam("ispravno") boolean ispravno,
                                Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        // Stvaranje instance Oprema objekta
        Oprema oprema = new Oprema();
        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setVrsta(vrstaRepository.findById(vrstaId).orElse(null));
        oprema.setProizvodjac(proizvodjacRepository.findById(proizvodjacId).orElse(null));
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setCertifikat(certifikat);
        oprema.setIspravno(ispravno);
        oprema.setNaUmjeravanju(false);
        oprema.setVlasnik(vlasnikRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        oprema.setRadiliste(user.getRadiliste());
        oprema.setNaServisu(false);
        oprema.setUps(ups);
        oprema.setOtpisano(false);
        opremaRepository.save(oprema);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }


    @GetMapping("/z-unosnoveopreme")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String zunosnoveopreme(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        List<Vrsta> vrste = vrstaRepository.findByRadiliste(user.getRadiliste());
        List<Proizvodjac> proizvodjaci = proizvodjacRepository.findByRadiliste(user.getRadiliste());
        List<Vlasnik> vlasnici = vlasnikRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);
        model.addAttribute("user", user);
        return "z-unos_nove_opreme.html";
    }

}
