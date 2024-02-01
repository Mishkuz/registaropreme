package com.HITA.bazaOpreme;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.repository.OpremaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @GetMapping("/prijavakvarapocetna")
        public String prijavikvar() {
            return "unos_prijave_kvara.html";
        }


    @GetMapping("/dodajuredaj")
        public String dodajuredaj() {
            return "dodajuredaj.html";
        }



    @GetMapping("/evidencijaodrzavanjapocetna")
    public String evidencijaodrzavanja() {
            return "evidencijaodrzavanja.html";
        }



}