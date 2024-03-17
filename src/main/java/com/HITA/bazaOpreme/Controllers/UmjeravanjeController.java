package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Odrzavanje;
import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.model.Serviser;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class UmjeravanjeController {

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
    @Autowired
    private KorisnikRepository korisnikRepository;

    @GetMapping("/z-spremiUmjeravanje")
    public String zspremiUmjeravanje(
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("serviserId") Long serviserId,
            @RequestParam(value = "umjerioRadnik", required = false) String umjerioRadnik,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
            @RequestParam("dateP") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumPovrata,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Odrzavanje o = new Odrzavanje();
        o.setRadiliste(user.getRadiliste());
        o.setServiser(serviserRepository.findById(serviserId).orElse(null));
        o.setTip(umjeravanjeS);
        o.setOprema(opremaRepository.findById(opremaId).orElse(null));
        o.setRadnik(umjerioRadnik);
        o.setDatumOtpreme(datumOtpreme);
        o.setDatumPovrata(datumPovrata);

        odrzavanjeRepository.save(o);
        opremaRepository.updateNaUmjeravanjuById(false, opremaId);
        model.addAttribute("user", user);
        return "redirect:/opremaNaUmjeravanju";
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
        model.addAttribute("user", user);
        return "z-unos_za_umjeravanje.html";
    }

    @GetMapping("/staviNaUmjeravanje")
    public String sNU(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        opremaRepository.updateNaUmjeravanjuById(true, opremaId);
        model.addAttribute("user", user);
        return "redirect:/opremaNaUmjeravanju";
    }

    @GetMapping("/opremaNaUmjeravanju")
    public String opnU(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList = opremaRepository.findByRadilisteAndNaUmjeravanju(user.getRadiliste(), true);

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "oprema_na_umjeravanju.html";
    }
}


