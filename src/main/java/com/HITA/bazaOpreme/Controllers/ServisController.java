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

        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Serviser> serviseri = null;
        PrivOd p = privOdRepository.findByOprema_Id(opremaId);
        Serviser s = p.getServiser();
        serviseri.add(s);
        LocalDate l = p.getDatumOtpreme();
        model.addAttribute("datumOtpreme",l);
        model.addAttribute(odrzavanjeList);
        model.addAttribute(opremaList);
        model.addAttribute("serviseri", serviseri);
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
                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
                       HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Oprema o = opremaRepository.findById(opremaId).orElse(null);
        Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
        PrivOd p = new PrivOd();
        privOdRepository.save(p);
        opremaRepository.updateNaServisuById(true, opremaId);
        model.addAttribute("user", user);
        model.addAttribute("oprema", opremaRepository.findById(opremaId));

        return "redirect:/evidencijaOpremeNaServisu";
    }

    @GetMapping("/z-spremiServis")
    public String zspremiServis(
            @RequestParam("tvrtkaId") Long tvrtkaId,
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
            @RequestParam("dateP") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumPovrata,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");


        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, opisOdrzavanja, null, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setServiser(serviserRepository.findById(tvrtkaId).orElse(null));
        odrzavanje.setDatumOtpreme(datumOtpreme);
        odrzavanje.setDatumPovrata(datumPovrata);
        odrzavanje.setDatumPlaniranogServisiranja(opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja());
        privOdRepository.deleteByOprema(opremaRepository.findById(opremaId));

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
            LocalDate l = o.get().getDatumPlaniranogServisiranja();
            int iFinal = i * 30;
            l = l.plusDays(iFinal);
            opremaRepository.updateDatumPlaniranogServisiranjaById(l, opremaId);
        }
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
