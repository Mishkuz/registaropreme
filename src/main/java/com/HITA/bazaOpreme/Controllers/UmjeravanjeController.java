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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private final PrivOdRepository privOdRepository;


    private final String servisS = "Servis";
    private final String servisIzvanredanS = "Izvanredan servis";
    private final String umjeravanjeS = "umjeravanje";
    @Autowired
    private KorisnikRepository korisnikRepository;


    @GetMapping("/z-spremiUmjeravanje")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String zspremiUmjeravanje(
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam(value = "umjerioRadnik", required = false) String umjerioRadnik,
            @RequestParam("dateP") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumPovrata,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        PrivOd p = privOdRepository.findByOprema_Id(opremaId);

        Odrzavanje o = new Odrzavanje();
        o.setRadiliste(user.getRadiliste());
        o.setServiser(serviserRepository.findById(p.getServiser().getId()).orElse(null));
        o.setTip(umjeravanjeS);
        o.setOprema(opremaRepository.findById(opremaId).orElse(null));
        o.setRadnik(umjerioRadnik);
        o.setDatumOtpreme(p.getDatumOtpreme());
        o.setDatumPovrata(datumPovrata);
        o.setOpisOdrzavanja(opisOdrzavanja);
        o.setDatumPlaniranogServisiranja(opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja());
        odrzavanjeRepository.save(o);

        LocalDate l = opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja();
        int i = opremaRepository.findById(opremaId).get().getIntervalServisiranjaUMjesecima();
        if (i == 12) {
            datumPovrata = datumPovrata.plusYears(1);
            opremaRepository.updateDatumPlaniranogServisiranjaById(datumPovrata, opremaId);
        } else {
            long longI = (long) i;
            datumPovrata = datumPovrata.plusMonths(longI);
            opremaRepository.updateDatumPlaniranogServisiranjaById(datumPovrata, opremaId);
        }
        opremaRepository.updateCertifikatById(true, opremaId);
        privOdRepository.delete(p);
        opremaRepository.updateNaUmjeravanjuById(false, opremaId);
        model.addAttribute("user", user);
        return "redirect:/opremaNaUmjeravanju";
    }


    @GetMapping("/z-spremiPriUmjeravanje")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sPNUs(Model model,
                        @RequestParam("tvrtkaId") Long tvrtkaId,
                        @RequestParam("opremaId") Long opremaId,
                        @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datumOtpreme,
                        @RequestParam(name = "toggleInput", required = false,defaultValue = "false") boolean toggleInput,
                        @RequestParam(name = "umjerioRadnik", required = false) String umjerioRadnik,
                        @RequestParam(name = "opisOdrzavanja", required = false) String opis,
                        HttpSession session) {

        Korisnik user = (Korisnik) session.getAttribute("currUser");
        //kada je toggleInput checkan ond ato znači da je višednevno održavanje te se oprema zatim zapisuje u privOd(Privremeno održavanje)
        if (toggleInput) {
            Oprema o = opremaRepository.findById(opremaId).orElse(null);
            Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
            PrivOd p = new PrivOd();
            p.setOprema(o);
            p.setServiser(s);
            p.setDatumOtpreme(datumOtpreme);
            privOdRepository.save(p);
            opremaRepository.updateNaUmjeravanjuById(true, opremaId);
            return "redirect:/opremaNaUmjeravanju";
        } else if(!toggleInput){
            Oprema o = opremaRepository.findById(opremaId).orElse(null);
            Serviser s = serviserRepository.findById(tvrtkaId).orElse(null);
            Odrzavanje odrzavanje = new Odrzavanje();
            odrzavanje.setDatumPovrata(datumOtpreme);
            odrzavanje.setTip(umjeravanjeS);
            odrzavanje.setServiser(s);
            odrzavanje.setOprema(o);
            odrzavanje.setOpisOdrzavanja(opis);
            odrzavanje.setRadnik(umjerioRadnik);
            odrzavanje.setRadiliste(user.getRadiliste());
            odrzavanje.setDatumPlaniranogServisiranja(o.getDatumPlaniranogServisiranja());
            o.setNaUmjeravanju(false);
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


    @GetMapping("/z-unos_za_umjeravanje")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String zunos_za_umjeravanje(Model model, @RequestParam("opremaId") Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");


        PrivOd p = privOdRepository.findByOprema_Id(opremaId);
        Serviser s = p.getServiser();

        LocalDate l = p.getDatumOtpreme();

        model.addAttribute("datumOtpreme", l);
        model.addAttribute("serviser", s);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        model.addAttribute("user", user);
        return "z-unos_za_umjeravanje.html";
    }



    @GetMapping("/staviNaUmjeravanje")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sNU(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());

        opremaRepository.updateNaUmjeravanjuById(true, opremaId);
        model.addAttribute("user", user);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute("oprema", opremaRepository.findById(opremaId));

        return "z-unos_za_umjeravanje1.html";
    }



    @GetMapping("/opremaNaUmjeravanju")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String opnU(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList = opremaRepository.findByRadilisteAndNaUmjeravanju(user.getRadiliste(), true);

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "oprema_na_umjeravanju.html";
    }
}


