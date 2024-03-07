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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return "z-prijava_kvara.html";
    }

    @PostMapping("/z-spremiuredaj")
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
        oprema.setVlasnik(vlasnikRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        oprema.setRadiliste(user.getRadiliste());
        oprema.setNaServisu(false);
        opremaRepository.save(oprema);
        return "redirect:/";
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
        return "z-unos_nove_opreme.html";
    }

    @GetMapping("/z-evidencija_servisa")
    public String zevidencijaodrzavanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTipOrTip(user.getRadiliste(), servisS, servisIzvanredanS);
        odrzavanjeList1.sort(Comparator.comparing(Odrzavanje::getDatumOtpreme, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList1);
        return "z-evidencija_servisa.html";
    }

    @GetMapping("/z-evidencija_umjeravanja")
    public String zevidencijaumjeravanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTip(user.getRadiliste(), umjeravanjeS);
        odrzavanjeList1.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja, Comparator.reverseOrder()));
        model.addAttribute("odrzavanjeList", odrzavanjeList1);
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
        return "redirect:/z-pokaziKvarove";
    }

    @GetMapping("/z-unos_za_servis")
    public String zunos_za_servis(Model model, Long opremaId, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Oprema> opremaList = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByRadiliste(user.getRadiliste());
        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute(odrzavanjeList);
        model.addAttribute(opremaList);
        model.addAttribute("serviseri", serviseri);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "z-unos_za_servis.html";
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
        return "z-unos_za_umjeravanje.html";
    }

    @GetMapping("/z-spremiServis")
    public String zspremiServis(
            @RequestParam("tvrtkaId") Long tvrtkaId,
            @RequestParam("opremaId") Long opremaId,
            @RequestParam("opisOdrzavanja") String opisOdrzavanja,
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumOtpreme") LocalDate datumOtpreme,
            @RequestParam("izvanredan") boolean izvanredan,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        LocalDate ld = LocalDate.now();
        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, opisOdrzavanja,ld, null, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setServiser(serviserRepository.findById(tvrtkaId).orElse(null));
        odrzavanje.setDatumPlaniranogServisiranja(opremaRepository.findById(opremaId).get().getDatumPlaniranogServisiranja());
        opremaRepository.updateNaServisuById(true, opremaId);
        if (izvanredan) {
            odrzavanje.setTip(servisIzvanredanS);
        } else if (!izvanredan) {
            odrzavanje.setTip(servisS);
        }
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencija_servisa";
    }

    @GetMapping("/z-spremiUmjeravanje")
    public String zspremiUmjeravanje(
            @RequestParam("prijavioRadnik") String prijavioRadnik,
            @RequestParam("datumUmjeravanja") LocalDate datumUmjeravanja,
            @RequestParam("opremaId") Long opremaId,
            Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        Odrzavanje odrzavanje = new Odrzavanje(prijavioRadnik, datumUmjeravanja, user.getRadiliste(), opremaRepository.findById(opremaId).get());
        odrzavanje.setTip(umjeravanjeS);
        odrzavanjeRepository.save(odrzavanje);
        return "redirect:/z-evidencija_umjeravanja";
    }


    @GetMapping("/z-pokaziKvarove")
    public String zshowFailures(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kvar> kvarList1 = kvarRepository.findByRadiliste(user.getRadiliste());
        List<Kvar> kvarList = new ArrayList<>(kvarList1);
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave, Comparator.reverseOrder()));
        model.addAttribute("kvarList", kvarList);
        return "z-oprema_kvarovi.html";
    }

    @GetMapping("/pocetna")
    public String nada(@AuthenticationPrincipal CustomKorisnikDetails customKorisnikDetails, Model model, HttpSession session) {
        Korisnik user = korisnikRepository.findByEmail(customKorisnikDetails.getUsername());
        session.setAttribute("currUser", user);
        List<Oprema> opremaList1 = opremaRepository.findByRadiliste(user.getRadiliste());
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-pocetna.html";
    }


    @GetMapping("/evidencijaOpremeNaServisu")
    public String evidencijaOpremeNaServisu(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-evidencija_opreme_na_servisu.html";
    }


    @GetMapping("/z-vratiSaServisa")
    public String vratiSaServisa(Model model, HttpSession session, @RequestParam("opremaId") Long opremaId) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        opremaRepository.updateNaServisuById(false, opremaId);
        opremaRepository.updateIspravnoById(true, opremaId);
        int i = opremaRepository.findById(opremaId).get().getIntervalServisiranjaUMjesecima();
        Optional<Oprema> o = opremaRepository.findById(opremaId);
        LocalDate l = o.get().getDatumPlaniranogServisiranja();
        int iFinal = i * 30;
        l.plusDays(iFinal);
        opremaRepository.updateDatumPlaniranogServisiranjaById(l, opremaId);
        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndNaServisu(user.getRadiliste(), true);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));
        model.addAttribute("opremaList", opremaList);
        return "z-evidencija_opreme_na_servisu.html";
    }

    @GetMapping("/z-pregled_pojedine_opreme")
    public String prikaziDetaljeOpreme(Long opremaId, Model model) {
        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);
        model.addAttribute("oprema", oprema);
        return "z-pregled_pojedine_opreme";
    }


    @GetMapping("/spremiNovuKategoriju")
    public String spremiNovuKategoriju(@RequestParam("sifra") String sifra,
                                       @RequestParam("naziv") String naziv,
                                       HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        if (user == null) {
            return "redirect:/login";
        }

        Kategorija novaKategorija = new Kategorija();
        novaKategorija.setSifra(sifra);
        novaKategorija.setKategorija(naziv);
        novaKategorija.setRadiliste(user.getRadiliste());

        kategorijaRepository.save(novaKategorija);

        return "redirect:/pocetna";
    }

    @GetMapping("/unosNoveKategorije")
    public String unosKategorije(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        return "z-unos_kategorije";
    }

    @GetMapping("/unosNoveVrste")
    public String unosVrste(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");
        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
        model.addAttribute("kategorije", kategorije);
        return "z-unos_vrste";
    }

    @GetMapping("/spremiNovuVrstu")
    public String unosNoveVrste(@RequestParam("sifra") String sifra,
                                @RequestParam("naziv") String naziv,
                                @RequestParam("kategorijaId") Long kategorijaId,
                                HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        if (user == null) {
            return "redirect:/login";
        }
        Vrsta novaVrsta = new Vrsta();
        novaVrsta.setSifra(sifra);
        novaVrsta.setVrsta(naziv);
        novaVrsta.setRadiliste(user.getRadiliste());
        novaVrsta.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        vrstaRepository.save(novaVrsta);
        return "redirect:/pocetna";
    }

}

