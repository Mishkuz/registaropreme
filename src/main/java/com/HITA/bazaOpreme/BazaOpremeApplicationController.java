package com.HITA.bazaOpreme;

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


@GetMapping("/pocetna")
    public String pocetna() {
        return "pocetna.html";
    }


@GetMapping("/prijavakvarapocetna")
    public String prijavikvar() {
        return "prijavikvar.html";
    }


@GetMapping("/dodajuredaj")
    public String dodajuredaj() {
        return "dodajuredaj.html";
    }






}