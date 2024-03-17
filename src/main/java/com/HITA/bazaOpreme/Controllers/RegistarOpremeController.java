package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import com.HITA.bazaOpreme.service.CustomKorisnikDetails;
import com.HITA.bazaOpreme.service.CustomKorisnikDetailsService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@AllArgsConstructor
public class RegistarOpremeController {

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


    @GetMapping("/z-unos_novog_kvara")
    public String zunos_novog_kvara(Model model, @RequestParam(name = "opremaId") Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Kvar> kvarList = kvarRepository.findByRadiliste(user.getRadiliste());
        Optional<Oprema> oprema = opremaRepository.findById(opremaId);

        if (oprema.isPresent()) {
            model.addAttribute("oprema", oprema.get());
        } else {
            // Ovdje obradite slučaj kada oprema s traženim ID-em nije pronađena
        }

        model.addAttribute("kvarList", kvarList);
        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-prijava_kvara.html";
    }

    @GetMapping("/z-spremiuredaj")
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
                                @RequestParam("ups") String ups,
                                @RequestParam("intervalServisiranjaUMjesecima") Integer intervalServisiranjaUMjesecima,
                                @RequestParam("datumPlaniranogServisiranja") LocalDate datumPlaniranogServisiranja,
                                @RequestParam("ispravno") boolean ispravno,
                                Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        // Stvaranje instance Oprema objekta
        Oprema oprema = new Oprema();
        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setVrsta(vrstaRepository.findById(vrstaId).orElse(null));
        oprema.setProizvodjac(proizvodjacRepository.findById(proizvodjacId).orElse(null));
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setCertifikat(certifikat);
        oprema.setIspravno(ispravno);
        oprema.setNaUmjeravanju(false);
        oprema.setVlasnik(vlasnikRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        oprema.setRadiliste(user.getRadiliste());
        oprema.setNaServisu(false);
        oprema.setUps(ups);
        opremaRepository.save(oprema);
        model.addAttribute("user", user);
        return "redirect:/pocetna";
    }

    @GetMapping("/z-unosnoveopreme")
    public String zunosnoveopreme(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        List<Vrsta> vrste = vrstaRepository.findByRadiliste(user.getRadiliste());
        List<Proizvodjac> proizvodjaci = proizvodjacRepository.findByRadiliste(user.getRadiliste());
        List<Vlasnik> vlasnici = vlasnikRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);
        model.addAttribute("user", user);
        return "z-unos_nove_opreme.html";
    }



    @GetMapping("/z-evidencija_umjeravanja")
    public String zevidencijaumjeravanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTip(user.getRadiliste(), umjeravanjeS);
        odrzavanjeList1.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja, Comparator.reverseOrder()));

        model.addAttribute("odrzavanjeList", odrzavanjeList1);
        model.addAttribute("user", user);
        return "z-evidencija_umjeravanja.html";
    }

    @GetMapping("/z-spremiKvar")
    public String zspremiKvar(@RequestParam("opremaId") Long opremaId,
                              @RequestParam("prijavioRadnik") String prijavioRadnik,
                              @RequestParam("opisKvara") String opisKvara,
                              @RequestParam("datumPrijave") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumPrijave,
                              Model model, HttpSession session) {
        opremaRepository.updateIspravnoById(false, opremaId);
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Kvar kvar = new Kvar(prijavioRadnik, opisKvara, null, user.getRadiliste());
        kvar.setPrijavioRadnik(prijavioRadnik);
        kvar.setOpisKvara(opisKvara);
        kvar.setDatumPrijave(datumPrijave);
        kvar.setOprema(opremaRepository.findById(opremaId).get());
        kvarRepository.save(kvar);
        model.addAttribute("user", user);
        return "redirect:/z-pokaziKvarove";
    }



    @GetMapping("/z-pokaziKvarove")
    public String zshowFailures(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Kvar> kvarList1 = kvarRepository.findByRadiliste(user.getRadiliste());
        List<Kvar> kvarList = new ArrayList<>(kvarList1);
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave, Comparator.reverseOrder()));

        model.addAttribute("kvarList", kvarList);
        model.addAttribute("user", user);
        return "z-oprema_kvarovi.html";
    }

    @GetMapping("/pocetna")
    public String nada(@AuthenticationPrincipal CustomKorisnikDetails customKorisnikDetails, Model model, HttpSession session) {
        Korisnik user = korisnikRepository.findByEmail(customKorisnikDetails.getUsername());
        session.setAttribute("currUser", user);

        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndOtpisanoIsNull(user.getRadiliste());
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-pocetna.html";
    }


    @GetMapping("/z-pregled_pojedine_opreme")
    public String prikaziDetaljeOpreme(Long opremaId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByOpremaAndTip(oprema, umjeravanjeS);
        List<Odrzavanje> odrzavanjeSList = odrzavanjeRepository.findByOpremaAndTipOrOpremaAndTip(oprema, servisS, oprema, servisIzvanredanS);
        List<Kvar> kvarList = kvarRepository.findByOprema(oprema);
        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja));
        odrzavanjeSList.sort(Comparator.comparing(Odrzavanje::getDatumOtpreme));
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave));

        model.addAttribute("odrzavanjeSList", odrzavanjeSList);
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        model.addAttribute("kvarList", kvarList);
        model.addAttribute("oprema", oprema);
        model.addAttribute("user", user);
        return "z-pregled_pojedine_opreme";
    }


    @GetMapping("/popis_proizvodjaca")
    public String prikaziPopisProizvodjaca(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Proizvodjac> proizvodjaci = proizvodjacRepository.findAll();

        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("user", user);
        return "popis_proizvodjaca";
    }


    @GetMapping("/popis_servisera")
    public String prikaziPopisServisera(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Serviser> serviseri = serviserRepository.findAll();

        model.addAttribute("serviseri", serviseri);
        model.addAttribute("user", user);
        return "popis_servisera";
    }


    @GetMapping("/popis_vlasnika")
    public String prikaziPopisVlasnika(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Vlasnik> vlasnici = vlasnikRepository.findAll();

        model.addAttribute("vlasnici", vlasnici);
        model.addAttribute("user", user);
        return "popis_vlasnika";
    }


    @GetMapping("/pregled_pojedinog_proizvodjaca")
    public String prikaziDetaljeProizvodjaca(Long proizvodjacId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Proizvodjac proizvodjac = proizvodjacRepository.findById(proizvodjacId).orElse(null);

        model.addAttribute("proizvodjac", proizvodjac);
        model.addAttribute("user", user);
        return "pregled_pojedinog_proizvodjaca";
    }



    @GetMapping("/pregled_pojedinog_vlasnika")
    public String prikaziDetaljeVlasnika(Long vlasnikId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Vlasnik vlasnik = vlasnikRepository.findById(vlasnikId).orElse(null);

        model.addAttribute("vlasnik", vlasnik);
        model.addAttribute("user", user);
        return "pregled_pojedinog_vlasnika";
    }


    @GetMapping("/pregled_pojedinog_servisera")
    public String prikaziDetaljeServisera(Long serviserId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Serviser serviser = serviserRepository.findById(serviserId).orElse(null);

        model.addAttribute("serviser", serviser);
        model.addAttribute("user", user);
        return "pregled_pojedinog_servisera";
    }

    @GetMapping("/otpisi_opremu")
    public String otpisiOpremu(@RequestParam("opremaId") Long opremaId,HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);

        model.addAttribute("user", user);

        if (oprema != null) {
            oprema.setOtpisano(true);
            oprema.setDatumOtpisa(LocalDate.now());
            opremaRepository.save(oprema);
            return "redirect:/z-pregled_pojedine_opreme?opremaId=" + oprema.getId();
        } else {
            return "redirect:/error";
        }
    }


    @GetMapping("/pregled_otpisane_opreme")
    public String pregledOtpisaneOpreme(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> otpisanaOpremaList = opremaRepository.findByOtpisano(true);

        model.addAttribute("otpisanaOpremaList", otpisanaOpremaList);
        model.addAttribute("user", user);
        return "pregled_otpisane_opreme";
    }


    @GetMapping("/evidencija_opreme_u_kvaru")
    public String prikaziOpremuUKvaru(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaUKvaruList = opremaRepository.findByRadilisteAndIspravnoFalse(user.getRadiliste());

        model.addAttribute("opremaList", opremaUKvaruList);
        model.addAttribute("user", user);
        return "evidencija_opreme_u_kvaru";
    }

}

