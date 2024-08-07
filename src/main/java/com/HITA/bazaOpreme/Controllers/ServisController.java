package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public String listOfServices(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTipOrRadilisteAndTip(user.getRadiliste(), servisS, user.getRadiliste(), servisIzvanredanS);

        model.addAttribute("odrzavanjeList", odrzavanjeList1);
        model.addAttribute("user", user);
        return "z-evidencija_servisa.html";
    }


    @GetMapping("/z-unos_za_servis")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String inputForService(Model model, Long opremaId, HttpSession session) {
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public String putOnService(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());
        opremaRepository.updateNaServisuById(true, opremaId);
        model.addAttribute("user", user);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());

        return "z-unos_za_servis1.html";
    }

    @GetMapping("/z-spremiPriServis")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String saveTemporaryService(Model model,
                                       @RequestParam("tvrtkaId") Long tvrtkaId,
                                       @RequestParam("opremaId") Long opremaId,
                                       @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
                                       @RequestParam(name = "toggleInput", required = false, defaultValue = "false") boolean toggleInput,
                                       @RequestParam(name = "umjerioRadnik", required = false) String umjerioRadnik,
                                       @RequestParam(name = "opisOdrzavanja", required = false) String opis,
                                       HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        if (toggleInput) {
            Oprema o = opremaRepository.findById(opremaId).orElse(null);
            Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
            PrivOd p = new PrivOd();
            p.setOprema(o);
            p.setServiser(s);
            p.setDatumOtpreme(datumOtpreme);
            privOdRepository.save(p);
            opremaRepository.updateNaServisuById(true, opremaId);

            return "redirect:/evidencijaOpremeNaServisu";
        } else if (!toggleInput) {
            Oprema o = opremaRepository.findById(opremaId).orElse(null);
            Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
            Odrzavanje odrzavanje = new Odrzavanje();
            odrzavanje.setDatumPovrata(datumOtpreme);
            odrzavanje.setTip(o.getIspravno() ? servisS : servisIzvanredanS);
            odrzavanje.setServiser(s);
            odrzavanje.setOprema(o);
            odrzavanje.setOpisOdrzavanja(opis);
            odrzavanje.setRadnik(umjerioRadnik);
            odrzavanje.setRadiliste(user.getRadiliste());
            odrzavanje.setDatumPlaniranogServisiranja(o.getDatumPlaniranogServisiranja());
            o.setNaServisu(false);
            o.setIspravno(true);
            opremaRepository.save(o);
            LocalDate l = opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja();
            int i = opremaRepository.findById(opremaId).get().getIntervalServisiranjaUMjesecima();
            if (i == 12) {
                datumOtpreme = datumOtpreme.plusYears(1);
                opremaRepository.updateDatumPlaniranogServisiranjaById(datumOtpreme, opremaId);
            } else {
                long longI = (long) i;
                datumOtpreme = datumOtpreme.plusMonths(longI);
                opremaRepository.updateDatumPlaniranogServisiranjaById(datumOtpreme, opremaId);
            }
            odrzavanjeRepository.save(odrzavanje);
            return "redirect:/pocetna";
        } else {
            return "redirect:/pocetna";
        }
    }


    @GetMapping("/z-spremiServis")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String saveService(
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam(name = "umjerioRadnik", required = false) String umjerioRadnik,
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


        opremaRepository.updateNaServisuById(false, opremaId);
        opremaRepository.updateNaUmjeravanjuById(false, opremaId);
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
        opremaRepository.updateCertifikatById(true, opremaId);
        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-evidencija_opreme_na_servisu.html";

    }

    @GetMapping("/evidencijaOpremeNaServisu")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String listEquipmentOnService(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-evidencija_opreme_na_servisu.html";
    }


}
