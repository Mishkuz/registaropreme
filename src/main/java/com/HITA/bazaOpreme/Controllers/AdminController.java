package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import com.HITA.bazaOpreme.service.CustomKorisnikDetails;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AdminController {

    private final OpremaRepository opremaRepository;
    private final KvarRepository kvarRepository;
    private final KategorijaRepository kategorijaRepository;
    private final OdrzavanjeRepository odrzavanjeRepository;
    private final VrstaRepository vrstaRepository;
    private final ProizvodjacRepository proizvodjacRepository;
    private final VlasnikRepository vlasnikRepository;
    private final ServiserRepository serviserRepository;
    private final String servisS = "Servis";
    private final String servisIzvanredanS = "Izvanredan servis";
    private final String umjeravanjeS = "umjeravanje";

    private final  KorisnikRepository korisnikRepository;
    private final UstanovaRepository ustanovaRepository;
    private final RadilisteRepository radilisteRepository;


    @GetMapping("/admin/z-unos_novog_kvara")
    public String adminzunos_novog_kvara(Model model, @RequestParam(name = "opremaId") Long opremaId, HttpSession session) {
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
        return "z-prijava_kvara.html";
    }

    @PostMapping("/admin/z-spremiuredaj")
    public String adminzspremiUredaj(@RequestParam("sifra") String sifra,
                                     @RequestParam("naziv") String naziv,
                                     @RequestParam("serijskiBroj") String serijskiBroj,
                                     @RequestParam("inventarskiBroj") String inventarskiBroj,
                                     @RequestParam("kategorijaId") Long kategorijaId,
                                     @RequestParam("vrstaId") Long vrstaId,
                                     @RequestParam("proizvodjacId") Long proizvodjacId,
                                     @RequestParam("godinaProizvodnje") LocalDate godinaProizvodnje,
                                     @RequestParam("datumNabave") LocalDate datumNabave,
                                     @RequestParam("certifikat") boolean certifikat,
                                     @RequestParam("vlasnikId") Long vlasnikId,
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
        oprema.setVlasnik(vlasnikRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        oprema.setRadiliste(user.getRadiliste());
        oprema.setNaServisu(false);
        opremaRepository.save(oprema);
        return "redirect:/";
    }

    @GetMapping("/admin/z-unosnoveopreme")
    public String adminzunosnoveopreme(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        List<Vrsta> vrste = vrstaRepository.findByRadiliste(user.getRadiliste());
        List<Proizvodjac> proizvodjaci = proizvodjacRepository.findByRadiliste(user.getRadiliste());
        List<Vlasnik> vlasnici = vlasnikRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);
        return "z-unos_nove_opreme.html";
    }

    @GetMapping("/admin/z-evidencija_servisa")
    public String adminzevidencijaodrzavanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Odrzavanje> odrzavanjeList = null;
        for (Radiliste r : radilisteList) {
            List<Odrzavanje> o = odrzavanjeRepository.findByRadilisteAndTipOrTip(r, servisS, servisIzvanredanS);
            for (Odrzavanje odrzavanje : o)
                odrzavanjeList.add(odrzavanje);
        }
        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumOtpreme, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        return "z-evidencija_servisa.html";
    }

    @GetMapping("/admin/z-evidencija_umjeravanja")
    public String adminevidencijaumjeravanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Odrzavanje> odrzavanjeList = null;
        for (Radiliste r : radilisteList) {
            List<Odrzavanje> o = odrzavanjeRepository.findByRadilisteAndTip(r, umjeravanjeS);
            for (Odrzavanje odrzavanje : o)
                odrzavanjeList.add(odrzavanje);
        }
        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        return "z-evidencija_umjeravanja.html";
    }

    @GetMapping("/admin/z-spremiKvar")
    public String adminzspremiKvar(@RequestParam("opremaId") Long opremaId,
                                   @RequestParam("prijavioRadnik") String prijavioRadnik,
                                   @RequestParam("opisKvara") String opisKvara,
                                   @RequestParam("datumPrijave") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPrijave,
                                   Model model, HttpSession session) {
        opremaRepository.updateIspravnoById(false, opremaId);
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Kvar kvar = new Kvar(prijavioRadnik, opisKvara, null, user.getRadiliste());
        kvar.setPrijavioRadnik(prijavioRadnik);
        kvar.setOpisKvara(opisKvara);
        kvar.setDatumPrijave(datumPrijave);
        kvar.setOprema(opremaRepository.findById(opremaId).get());
        kvarRepository.save(kvar);
        return "redirect:/z-pokaziKvarove";
    }

    @GetMapping("/admin/z-unos_za_servis")
    public String adminzunos_za_servis(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute(odrzavanjeList);
        model.addAttribute(opremaList);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "z-unos_za_servis.html";
    }

    @GetMapping("/admin/z-unos_za_umjeravanje")
    public String adminzunos_za_umjeravanje(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute(odrzavanjeList);
        model.addAttribute(opremaList);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "z-unos_za_umjeravanje.html";
    }

    @GetMapping("/admin/z-spremiServis")
    public String adminzspremiServis(
            @RequestParam("tvrtkaId") Long tvrtkaId,
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumOtpreme") LocalDate datumOtpreme,
            @RequestParam("izvanredan") boolean izvanredan,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
    /*    Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, opisOdrzavanja, datumOtpreme, null, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setServiser(serviserRepository.findById(tvrtkaId).orElse(null));
        opremaRepository.updateNaServisuById(true, opremaId);
        if (izvanredan) {
            odrzavanje.setTip(servisIzvanredanS);
        } else if (!izvanredan) {
            odrzavanje.setTip(servisS);
        }
        odrzavanjeRepository.save(odrzavanje);*/
        return "redirect:/z-evidencija_servisa";
    }

    @GetMapping("/admin/z-spremiUmjeravanje")
    public String adminzspremiUmjeravanje(
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumUmjeravanja") LocalDate datumUmjeravanja,
            @RequestParam("opremaId") Long opremaId,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, datumUmjeravanja, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setTip(umjeravanjeS);
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencija_umjeravanja";
    }


    @GetMapping("/admin/z-pokaziKvarove")
    public String adminzshowFailures(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Kvar> kvarList = null;
        for (Radiliste r : radilisteList) {
            List<Kvar> o = kvarRepository.findByRadiliste(r);
            for (Kvar kvar : o)
                kvarList.add(kvar);
        }
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave, Comparator.reverseOrder()));
        model.addAttribute("kvarList", kvarList);
        return "z-oprema_kvarovi.html";
    }

    @GetMapping("/admin/pocetna")
    public String adminnada(@AuthenticationPrincipal CustomKorisnikDetails customKorisnikDetails, Model model, HttpSession session) {
        Korisnik user = korisnikRepository.findByEmail(customKorisnikDetails.getUsername());
        session.setAttribute("currUser", user);
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Oprema> opremaList = null;
        for (Radiliste r : radilisteList) {
            List<Oprema> o = opremaRepository.findByRadiliste(r);
            for (Oprema oprema : o)
                opremaList.add(oprema);
        }
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-pocetna.html";
    }


    @GetMapping("/admin/evidencijaOpremeNaServisu")
    public String evidencijaOpremeNaServisu(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Oprema> opremaList = null;
        for (Radiliste r : radilisteList) {
            List<Oprema> o = opremaRepository.findByRadilisteAndNaServisu(r, true);
            for (Oprema oprema : o)
                opremaList.add(oprema);
        }
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-evidencija_opreme_na_servisu.html";
    }


    @GetMapping("/admin/z-vratiSaServisa")
    public String vratiSaServisa(Model model, HttpSession session, @RequestParam("opremaId") Long opremaId) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        opremaRepository.updateNaServisuById(false, opremaId);
        opremaRepository.updateIspravnoById(true, opremaId);
        Radiliste radiliste = user.getRadiliste();
        Ustanova ustanova = radiliste.getUstanova();
        List<Radiliste> radilisteList = radilisteRepository.findByUstanova(ustanova);
        List<Oprema> opremaList = null;
        for (Radiliste r : radilisteList) {
            List<Oprema> o = opremaRepository.findByRadilisteAndNaServisu(r, true);
            for (Oprema oprema : o)
                opremaList.add(oprema);
        }
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-evidencija_opreme_na_servisu.html";
    }

}
