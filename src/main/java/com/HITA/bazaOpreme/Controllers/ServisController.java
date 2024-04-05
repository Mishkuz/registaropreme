package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ServisController {
    private final OpremaRepository opremaRepository;
    private final KvarRepository kvarRepository;
    private final KategorijaRepository kategorijaRepository;
    private final OdrzavanjeRepository odrzavanjeRepository;
    private final VrstaRepository vrstaRepository;
    private final ProizvodjacRepository proizvodjacRepository;
    private final VlasnikRepository vlasnikRepository;
    private final ServiserRepository serviserRepository;
    private final PrivOdRepository privOdRepository;


    private final String servisS = "Servis";
    private final String servisIzvanredanS = "Izvanredan servis";
    private final String umjeravanjeS = "umjeravanje";
    @Autowired
    private KorisnikRepository korisnikRepository;


    @GetMapping("/z-evidencija_servisa")
    public String zevidencijaodrzavanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTipOrRadilisteAndTip(user.getRadiliste(), servisS, user.getRadiliste(), servisIzvanredanS);

        model.addAttribute("odrzavanjeList", odrzavanjeList1);
        model.addAttribute("user", user);
        return "z-evidencija_servisa.html";
    }


    @GetMapping("/z-unos_za_servis")
    public String zunos_za_servis(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");


        PrivOd p = privOdRepository.findByOprema_Id(opremaId);
        Serviser s = p.getServiser();

        LocalDate l = p.getDatumOtpreme();

        model.addAttribute("datumOtpreme", l);
        model.addAttribute("serviser", s);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        model.addAttribute("user", user);
        return "z-unos_za_servis.html";
    }


    @GetMapping("/staviNaServis")
    public String sNs(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());
        opremaRepository.updateNaServisuById(true, opremaId);
        model.addAttribute("user", user);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());

        return "z-unos_za_servis1.html";
    }

    @GetMapping("/z-spremiPriServis")
    public String sPNs(Model model,
                       @RequestParam("tvrtkaId") Long tvrtkaId,
                       @RequestParam("opremaId") Long opremaId,
                       @RequestParam(value = "date",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
                       HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Oprema o = opremaRepository.findById(opremaId).orElse(null);
        Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
        PrivOd p = new PrivOd();
        p.setOprema(o);
        p.setServiser(s);
        p.setDatumOtpreme(datumOtpreme);
        privOdRepository.save(p);
        opremaRepository.updateNaServisuById(true, opremaId);

        return "redirect:/evidencijaOpremeNaServisu";
    }

    @GetMapping("/z-spremiServis")
    public String zspremiServis(
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("umjerioRadnik") String umjerioRadnik,
            @RequestParam("dateP") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumPovrata,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        PrivOd p = privOdRepository.findByOprema_Id(opremaId);

        Odrzavanje odrzavanje = new Odrzavanje(umjerioRadnik, opisOdrzavanja, p.getDatumOtpreme(), user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setServiser(p.getServiser());
        odrzavanje.setDatumOtpreme(p.getDatumOtpreme());
        odrzavanje.setDatumPovrata(datumPovrata);
        odrzavanje.setDatumPlaniranogServisiranja(opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja());


        boolean b = opremaRepository.findById(opremaId).get().getIspravno();
        if (b == false) {
            odrzavanje.setTip(servisIzvanredanS);
        } else if (b == true) {
            odrzavanje.setTip(servisS);
        }

        odrzavanjeRepository.save(odrzavanje);
        model.addAttribute("user", user);

        opremaRepository.updateNaServisuById(false, opremaId);
        opremaRepository.updateIspravnoById(true, opremaId);
        if (b = true) {
            int i = opremaRepository.findById(opremaId).get().getIntervalServisiranjaUMjesecima();
            Optional<Oprema> o = opremaRepository.findById(opremaId);
            if (i == 12) {
                datumPovrata = datumPovrata.plusYears(1);
                opremaRepository.updateDatumPlaniranogServisiranjaById(datumPovrata, opremaId);
            } else {
                long longI = (long) i;
                datumPovrata = datumPovrata.plusMonths(longI);
                opremaRepository.updateDatumPlaniranogServisiranjaById(datumPovrata, opremaId);
            }
        }
        privOdRepository.delete(p);
        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-evidencija_opreme_na_servisu.html";

    }

    @GetMapping("/evidencijaOpremeNaServisu")
    public String evidencijaOpremeNaServisu(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-evidencija_opreme_na_servisu.html";
    }


}
