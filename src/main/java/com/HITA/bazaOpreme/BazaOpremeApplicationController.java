package com.HITA.bazaOpreme;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.repository.OpremaRepository;
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
                               @RequestParam("godinaProizvodnje") LocalDate godinaProizvodnje,
                               @RequestParam("datumNabave") LocalDate datumNabave,
                               @RequestParam("intervalServisiranjaUMjesecima") Integer intervalServisiranjaUMjesecima,
                               @RequestParam("datumPlaniranogServisiranja") LocalDate datumPlaniranogServisiranja,
                               @RequestParam("certifikat") boolean certifikat,


                               Model model) {

        // Stvaranje instance Oprema objekta
        Oprema oprema = new Oprema();
        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        oprema.setCertifikat(certifikat);

        // Postavite ostale atribute prema potrebi

        // Spremanje u bazu podataka
        opremaRepository.save(oprema);

        // Redirekcija na početnu stranicu ili drugu stranicu
        return "redirect:/pocetna";
    }
    @GetMapping("/prijavakvarapocetna")
        public String prijavikvar() {
            return "unos_prijave_kvara.html";
        }


    @GetMapping("/unosnoveopreme")
        public String unosnoveopreme() {
            return "unos_nove_opreme.html";
        }



    @GetMapping("/evidencijaodrzavanjapocetna")
    public String evidencijaodrzavanja() {
            return "evidencijaodrzavanja.html";
        }



}