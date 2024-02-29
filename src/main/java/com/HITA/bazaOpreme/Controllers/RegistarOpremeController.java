package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;

@Controller
public class RegistarOpremeController {
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
    ProizvodjacRepository proizvodjacRepository;
    @Autowired
    private VlasnikRepository vlasnikRepository;
    @Autowired
    private ServiserRepository serviserRepository;

    @GetMapping("/z-unos_novog_kvara")
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
        return "z-prijava_kvara.html";
    }

    @PostMapping("/z-spremiuredaj")
    public String zspremiUredaj(@RequestParam("sifra") String sifra,
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
        opremaRepository.save(oprema);
        return "redirect:/";
    }

    @GetMapping("/z-unosnoveopreme")
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
        return "z-unos_nove_opreme.html";
    }

    @GetMapping("/z-evidencija_servisa")
    public String zevidencijaodrzavanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = new ArrayList<>();

        // Iterirajte kroz listu i preskočite redak ako je datumOtpreme null
        for (Odrzavanje odrzavanje : odrzavanjeList1) {
            if (odrzavanje.getDatumOtpreme() != null) {
                odrzavanjeList.add(odrzavanje);
            }
        }

        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumOtpreme, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        return "z-evidencija_servisa.html";
    }
    @GetMapping("/z-evidencija_umjeravanja")
    public String zevidencijaumjeravanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = new ArrayList<>();

        // Iterirajte kroz listu i preskočite redak ako je datumUmjeravanja null
        for (Odrzavanje odrzavanje : odrzavanjeList1) {
            if (odrzavanje.getDatumUmjeravanja() != null) {
                odrzavanjeList.add(odrzavanje);
            }
        }

        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        return "z-evidencija_umjeravanja.html";
    }

    @PostMapping("/z-spremiKvar")
    public String zspremiKvar(@RequestParam("opremaId") Long opremaId,
                              @RequestParam("prijavioRadnik") String prijavioRadnik,
                              @RequestParam("opisKvara") String opisKvara,
                              @RequestParam("datumPrijave") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPrijave,
                              Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Kvar kvar = new Kvar(prijavioRadnik, opisKvara, null, user.getRadiliste());
        kvar.setPrijavioRadnik(prijavioRadnik);
        kvar.setOpisKvara(opisKvara);
        kvar.setDatumPrijave(datumPrijave);
        kvar.setOprema(opremaRepository.findById(opremaId).get());
        kvarRepository.save(kvar);
        return "redirect:/z-pokaziKvarove";
    }

    @GetMapping("/z-unos_za_servis")
    public String zunos_za_servis(Model model, Long opremaId, HttpSession session) {
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

    @GetMapping("/z-unos_za_umjeravanje")
    public String zunos_za_umjeravanje(Model model, Long opremaId, HttpSession session) {
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

    @GetMapping("/z-spremiServis")
    public String zspremiServis(
            @RequestParam("tvrtkaId") Long tvrtkaId,
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumOtpreme") LocalDate datumOtpreme,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");


        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, opisOdrzavanja, datumOtpreme, null, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setServiser(serviserRepository.findById(tvrtkaId).orElse(null));
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencija_servisa";
    }

    @GetMapping("/z-spremiUmjeravanje")
    public String zspremiUmjeravanje(
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumUmjeravanja") LocalDate datumUmjeravanja,
            @RequestParam("opremaId") Long opremaId,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik,datumUmjeravanja ,user.getRadiliste(),opremaRepository.findById(opremaId).get());
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencija_umjeravanja";
    }



    @GetMapping("/z-pokaziKvarove")
    public String zshowFailures(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kvar> kvarList1 = kvarRepository.findByRadiliste(user.getRadiliste());
        List<Kvar> kvarList = new ArrayList<>(kvarList1);
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave, Comparator.reverseOrder()));
        model.addAttribute("kvarList", kvarList);
        return "z-oprema_kvarovi.html";
    }

    @GetMapping("/pocetna")
    public String nada(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Oprema> opremaList1 = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-pocetna.html";
    }
}

