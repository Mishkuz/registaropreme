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
    private TvrtkaRepository tvrtkaRepository;

    @GetMapping("/z-unos_novog_kvara")
    public String zunos_novog_kvara(Model model, Long opremaId) {
        List<Oprema> opremaList = opremaRepository.findAll();
        List<Kvar> kvarList = kvarRepository.findAll();
        model.addAttribute(kvarList);
        model.addAttribute(opremaList);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "unos_novog_kvara.html";
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
                                Model model) {
        // Stvaranje instance Oprema objekta
        Oprema oprema = new Oprema();
        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setVrsta(vrstaRepository.findById(vrstaId).orElse(null));
        oprema.setTvrtkaProizvodjac(tvrtkaRepository.findById(proizvodjacId).orElse(null));
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setCertifikat(certifikat);
        oprema.setIspravno(ispravno);
        oprema.setTvrtkaVlasnik(tvrtkaRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        opremaRepository.save(oprema);
        return "redirect:/";
    }

    @GetMapping("/z-unosnoveopreme")
    public String zunosnoveopreme(Model model) {
        List<Kategorija> kategorije = kategorijaRepository.findAll();
        List<Vrsta> vrste = vrstaRepository.findAll();
        List<Tvrtka> proizvodjaci = tvrtkaRepository.findAll();
        List<Tvrtka> vlasnici = tvrtkaRepository.findAll();
        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);
        return "z-unos_nove_opreme.html";
    }

    @GetMapping("/z-evidencijaodrzavanja")
    public String zevidencijaodrzavanja(Model model) {
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findAll();
        model.addAttribute(odrzavanjeList);
        return "evidencijaodrzavanja.html";
    }

    @PostMapping("/z-spremiKvar")
    public String zspremiKvar(@RequestParam("opremaId") Long opremaId,
                              @RequestParam("prijavioRadnik") String prijavioRadnik,
                              @RequestParam("opisKvara") String opisKvara,
                              @RequestParam("datumPrijave") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPrijave,
                              Model model,
                              HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        Kvar kvar = new Kvar(prijavioRadnik, opisKvara, null);
        kvar.setPrijavioRadnik(prijavioRadnik);
        kvar.setOpisKvara(opisKvara);
        kvar.setDatumPrijave(datumPrijave);
        kvar.setOprema(opremaRepository.findById(opremaId).get());
        kvarRepository.save(kvar);
        return "redirect:/z-pokaziKvarove";
    }

    @GetMapping("/z-unos_za_odrzavanje")
    public String zunos_za_odrzavanje(Model model, Long opremaId) {
        List<Oprema> opremaList = opremaRepository.findAll();
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findAll();
        List<Tvrtka> serviseri = tvrtkaRepository.findAll();
        model.addAttribute(odrzavanjeList);
        model.addAttribute(opremaList);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "z-unos_za_odrzavanje.html";
    }

    @GetMapping("/z-spremiOdrzavanje")
    public String zspremiOdrzavanje(
            @RequestParam("tvrtkaId") Long tvrtkaId,
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("izvanredan") boolean izvanredan,
            @RequestParam("umjeravanje") boolean umjeravanje,
            @RequestParam("datumPrijave") String datumPrijave,
            Model model,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        // Stvaranje instance održavanja objekta
        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, opisOdrzavanja, izvanredan, umjeravanje,
                null, null, null);
        odrzavanje.setPrijavioRadnik(prijavioRadnik);
        odrzavanje.setOpisOdrzavanja(opisOdrzavanja);
        odrzavanje.setTvrtkaServiser(tvrtkaRepository.findById(tvrtkaId).orElse(null));
        odrzavanje.setIzvanredan(izvanredan);
        odrzavanje.setUmjeravanje(umjeravanje);
        odrzavanje.setDatumPrijave(new Date());
        odrzavanje.setOprema(opremaRepository.findById(opremaId).get());
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencijaodrzavanja";
    }

    @GetMapping("/z-pokaziKvarove")
    public String zshowFailures(Model model) {
        model.addAttribute("kvarList", kvarRepository.findAll());
        return "z-oprema_kvarovi.html";
    }

    @GetMapping("/")
    public String nada(Model model) {
        List<Oprema> opremaList1 = opremaRepository.findAll();
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList",opremaList);
        return "z-pocetna.html";
    }
}

