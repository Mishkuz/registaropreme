package com.HITA.bazaOpreme;

import com.HITA.bazaOpreme.model.Kategorija;
import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Tvrtka;
import com.HITA.bazaOpreme.model.Vrsta;
import com.HITA.bazaOpreme.model.archive.Proizvodjac;
import com.HITA.bazaOpreme.repository.KategorijaRepository;
import com.HITA.bazaOpreme.repository.OpremaRepository;
import com.HITA.bazaOpreme.repository.TvrtkaRepository;
import com.HITA.bazaOpreme.repository.VrstaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
    public class BazaOpremeApplicationController {

    @Autowired
    OpremaRepository opremaRepository;

    @Autowired
    KategorijaRepository kategorijaRepository;

    @Autowired
    VrstaRepository vrstaRepository;
    @Autowired
    private TvrtkaRepository tvrtkaRepository;


    @GetMapping("/pocetna")
    public String pocetna(Model model) {
        List<Oprema> opremaList = opremaRepository.findAll();
        model.addAttribute("opremaList",opremaList);
        return "pocetna.html";
    }

    @PostMapping("/spremiuredaj")
    public String spremiUredaj(@RequestParam("sifra") String sifra,
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
        oprema.setTvrtkaVlasnik(tvrtkaRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);



        opremaRepository.save(oprema);
        return "redirect:/pocetna";
    }
    @GetMapping("/prijavakvarapocetna")
        public String prijavikvar() {
            return "unos_prijave_kvara.html";
        }

    @GetMapping("/dodavanjenovoguredaja")
    public String dodavanjenovoguredaja() {return "unos_nove_opreme.html" ;}


    @GetMapping("/unosnoveopreme")
    public String unosnoveopreme(Model model) {

        List<Kategorija> kategorije = kategorijaRepository.findAll();
        List<Vrsta> vrste = vrstaRepository.findAll();
        List<Tvrtka> proizvodjaci = tvrtkaRepository.findAll();
        List<Tvrtka> vlasnici = tvrtkaRepository.findAll();

        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);

        return "unos_nove_opreme.html";
    }


    @GetMapping("/evidencijaodrzavanjapocetna")
    public String evidencijaodrzavanja() {
            return "evidencijaodrzavanja.html";
        }



}