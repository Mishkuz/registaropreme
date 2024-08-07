package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import com.HITA.bazaOpreme.service.CustomKorisnikDetails;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private KorisnikRepository korisnikRepository;


    private final String servisS = "Servis";
    private final String servisIzvanredanS = "Izvanredan servis";
    private final String umjeravanjeS = "umjeravanje";





    @GetMapping("/z-evidencija_umjeravanja")
    public String zevidencijaumjeravanja(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Odrzavanje> odrzavanjeList1 = odrzavanjeRepository.findByRadilisteAndTip(user.getRadiliste(), umjeravanjeS);

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
        return "redirect:/evidencija_opreme_u_kvaru";
    }



    @GetMapping("/z-pokaziKvarove")
    @PreAuthorize("hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public String nada(@AuthenticationPrincipal CustomKorisnikDetails customKorisnikDetails, Model model, HttpSession session) {
        Korisnik user = korisnikRepository.findByEmail(customKorisnikDetails.getUsername());
        session.setAttribute("currUser", user);

        List<Oprema> opremaList1 = opremaRepository.findByRadilisteAndIspravnoAndNaServisuAndNaUmjeravanjuAndOtpisano(user.getRadiliste(),true,false, false,false);
        List<Oprema> opremaList = new ArrayList<>(opremaList1);
        opremaList.sort(Comparator.comparing(Oprema::getDatumPlaniranogServisiranja));

        model.addAttribute("opremaList", opremaList);
        model.addAttribute("user", user);
        return "z-pocetna.html";
    }


    @GetMapping("/z-pregled_pojedine_opreme")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziDetaljeOpreme(Long opremaId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByOpremaAndTip(oprema, umjeravanjeS);
        List<Odrzavanje> odrzavanjeSList = odrzavanjeRepository.findByOpremaAndTipOrOpremaAndTip(oprema, servisS, oprema, servisIzvanredanS);
        List<Kvar> kvarList = kvarRepository.findByOprema(oprema);
        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumPovrata).reversed());
        odrzavanjeSList.sort(Comparator.comparing(Odrzavanje::getDatumPovrata).reversed());
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave).reversed());

        model.addAttribute("odrzavanjeSList", odrzavanjeSList);
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        model.addAttribute("kvarList", kvarList);
        model.addAttribute("oprema", oprema);
        model.addAttribute("user", user);
        return "z-pregled_pojedine_opreme";
    }


    @GetMapping("/popis_proizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziPopisProizvodjaca(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Proizvodjac> proizvodjaci = proizvodjacRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("user", user);
        return "popis_proizvodjaca";
    }


    @GetMapping("/popis_servisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziPopisServisera(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Serviser> serviseri = serviserRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("serviseri", serviseri);
        model.addAttribute("user", user);
        return "popis_servisera";
    }


    @GetMapping("/popis_vlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziPopisVlasnika(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Vlasnik> vlasnici = vlasnikRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("vlasnici", vlasnici);
        model.addAttribute("user", user);
        return "popis_vlasnika";
    }

    @GetMapping("/popis_vrsta")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziPopisVrsta(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Vrsta> vrste = vrstaRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("vrste", vrste);
        model.addAttribute("user", user);
        return "popis_vrsta";
    }

    @GetMapping("/popis_kategorija")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziPopisKategorija(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());

        model.addAttribute("kategorije", kategorije);
        model.addAttribute("user", user);
        return "popis_kategorija";
    }

    @GetMapping("/pregled_pojedinog_proizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziDetaljeProizvodjaca(Long proizvodjacId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Proizvodjac proizvodjac = proizvodjacRepository.findById(proizvodjacId).orElse(null);

        model.addAttribute("proizvodjac", proizvodjac);
        model.addAttribute("user", user);
        return "pregled_pojedinog_proizvodjaca";
    }



    @GetMapping("/pregled_pojedinog_vlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziDetaljeVlasnika(Long vlasnikId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Vlasnik vlasnik = vlasnikRepository.findById(vlasnikId).orElse(null);

        model.addAttribute("vlasnik", vlasnik);
        model.addAttribute("user", user);
        return "pregled_pojedinog_vlasnika";
    }


    @GetMapping("/pregled_pojedinog_servisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziDetaljeServisera(Long serviserId, HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Serviser serviser = serviserRepository.findById(serviserId).orElse(null);

        model.addAttribute("serviser", serviser);
        model.addAttribute("user", user);
        return "pregled_pojedinog_servisera";
    }

    @GetMapping("/otpisi_opremu")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String otpisiOpremu(@RequestParam("opremaId") Long opremaId,HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);

        model.addAttribute("user", user);

        if (oprema != null) {
            oprema.setOtpisano(true);
            oprema.setDatumOtpisa(LocalDate.now());
            opremaRepository.save(oprema);
            return "redirect:/pregled_otpisane_opreme";
        } else {
            return "redirect:/error";
        }
    }


    @GetMapping("/pregled_otpisane_opreme")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String pregledOtpisaneOpreme(HttpSession session, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> otpisanaOpremaList = opremaRepository.findByOtpisano(true);

        model.addAttribute("otpisanaOpremaList", otpisanaOpremaList);
        model.addAttribute("user", user);
        return "pregled_otpisane_opreme";
    }


    @GetMapping("/evidencija_opreme_u_kvaru")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziOpremuUKvaru(Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        List<Oprema> opremaUKvaruList = opremaRepository.findByRadilisteAndIspravnoFalse(user.getRadiliste());

        model.addAttribute("opremaList", opremaUKvaruList);
        model.addAttribute("user", user);
        return "evidencija_opreme_u_kvaru";
    }


    @GetMapping("/uredi_servisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziFormuZaUredjivanjeServisera(@RequestParam("serviserId") Long serviserId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Serviser> serviserOptional = serviserRepository.findById(serviserId);
        Serviser serviser = serviserOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji serviser s ID-om: " + serviserId));

        model.addAttribute("serviser", serviser);
        model.addAttribute("user", user);
        return "uredi_servisera";
    }

    @PostMapping("/spremiUredjenePodatkeServisera")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiUredjenePodatkeServisera(@RequestParam("id") Long id, @RequestParam("sifra") String sifra,
                                                 @RequestParam("naziv") String naziv, @RequestParam("adresa") String adresa,
                                                 @RequestParam("telefon") String telefon, @RequestParam("email") String email,
                                                 @RequestParam("kontaktOsoba") String kontaktOsoba, HttpSession session,
                                                 RedirectAttributes redirectAttributes, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Serviser> serviserOptional = serviserRepository.findById(id);
        Serviser serviser = serviserOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji serviser s ID-om: " + id));


        serviser.setSifra(sifra);
        serviser.setNaziv(naziv);
        serviser.setAdresa(adresa);
        serviser.setTelefon(telefon);
        serviser.setEmail(email);
        serviser.setKontaktOsoba(kontaktOsoba);


        serviserRepository.save(serviser);


        redirectAttributes.addFlashAttribute("message", "Podaci servisera su uspješno ažurirani.");
        model.addAttribute("user", user);
        return "redirect:/popis_servisera";
    }

    @GetMapping("/uredi_vlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziFormuZaUredjivanjeVlasnika(@RequestParam("vlasnikId") Long vlasnikId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Vlasnik> vlasnikOptional = vlasnikRepository.findById(vlasnikId);
        Vlasnik vlasnik = vlasnikOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji vlasnik s ID-om: " + vlasnikId));

        model.addAttribute("vlasnik", vlasnik);
        model.addAttribute("user", user);
        return "uredi_vlasnika";
    }

    @PostMapping("/spremiUredjenePodatkeVlasnika")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiUredjenePodatkeVlasnika(@RequestParam("id") Long id, @RequestParam("sifra") String sifra,
                                                 @RequestParam("naziv") String naziv, @RequestParam("adresa") String adresa,
                                                 @RequestParam("telefon") String telefon, @RequestParam("email") String email,
                                                 @RequestParam("kontaktOsoba") String kontaktOsoba, HttpSession session,
                                                 RedirectAttributes redirectAttributes, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Vlasnik> vlasnikOptional = vlasnikRepository.findById(id);
        Vlasnik vlasnik = vlasnikOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji vlasnik s ID-om: " + id));


        vlasnik.setSifra(sifra);
        vlasnik.setNaziv(naziv);
        vlasnik.setAdresa(adresa);
        vlasnik.setTelefon(telefon);
        vlasnik.setEmail(email);
        vlasnik.setKontaktOsoba(kontaktOsoba);


        vlasnikRepository.save(vlasnik);

        // Postavljanje poruke za korisnika
        redirectAttributes.addFlashAttribute("message", "Podaci vlasnika su uspješno ažurirani.");
        model.addAttribute("user", user);
        return "redirect:/popis_vlasnika";
    }


    @GetMapping("/uredi_proizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String prikaziFormuZaUredjivanjeProizvodjaca(@RequestParam("proizvodjacId") Long proizvodjacId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Proizvodjac> proizvodjacOptional = proizvodjacRepository.findById(proizvodjacId);
        Proizvodjac proizvodjac = proizvodjacOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji proizvodjac s ID-om: " + proizvodjacId));

        model.addAttribute("proizvodjac", proizvodjac);
        model.addAttribute("user", user);
        return "uredi_proizvodjaca";
    }


    @PostMapping("/spremiUredjenogProizvodjaca")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiUredjenogProizvodjaca(@RequestParam("id") Long id, @RequestParam("sifra") String sifra,
                                                @RequestParam("naziv") String naziv, @RequestParam("adresa") String adresa,
                                                @RequestParam("telefon") String telefon, @RequestParam("email") String email,
                                                @RequestParam("kontaktOsoba") String kontaktOsoba, HttpSession session,
                                                RedirectAttributes redirectAttributes, Model model) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Optional<Proizvodjac> proizvodjacOptional = proizvodjacRepository.findById(id);
        Proizvodjac proizvodjac = proizvodjacOptional.orElseThrow(() -> new IllegalArgumentException("Ne postoji proizvodjac s ID-om: " + id));


        proizvodjac.setSifra(sifra);
        proizvodjac.setNaziv(naziv);
        proizvodjac.setAdresa(adresa);
        proizvodjac.setTelefon(telefon);
        proizvodjac.setEmail(email);
        proizvodjac.setKontaktOsoba(kontaktOsoba);


        proizvodjacRepository.save(proizvodjac);


        redirectAttributes.addFlashAttribute("message", "Podaci proizvodjac su uspješno ažurirani.");
        model.addAttribute("user", user);
        return "redirect:/popis_proizvodjaca";
    }
    @GetMapping("/uredi_opremu")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String urediOpremu(@RequestParam("opremaId") Long opremaId, Model model, HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);

            List<Kategorija> kategorije = kategorijaRepository.findByRadiliste(user.getRadiliste());
            List<Vrsta> vrste = vrstaRepository.findByRadiliste(user.getRadiliste());
            List<Proizvodjac> proizvodjaci = proizvodjacRepository.findByRadiliste(user.getRadiliste());
            List<Vlasnik> vlasnici = vlasnikRepository.findByRadiliste(user.getRadiliste());

            model.addAttribute("oprema", oprema);
            model.addAttribute("kategorije", kategorije);
            model.addAttribute("vrste", vrste);
            model.addAttribute("proizvodjaci", proizvodjaci);
            model.addAttribute("vlasnici", vlasnici);
            model.addAttribute("user", user);

            return "uredi_opremu.html";
    }
    @GetMapping("/spremi_uredjenu_opremu")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String spremiUredjenuOpremu(@RequestParam("id") Long id,
                                       @RequestParam("sifra") String sifra,
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
                                       HttpSession session) {
        Korisnik user = (Korisnik) session.getAttribute("currUser");

        Oprema oprema = opremaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ne postoji oprema s ID-om: " + id));

        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setVrsta(vrstaRepository.findById(vrstaId).orElse(null));
        oprema.setProizvodjac(proizvodjacRepository.findById(proizvodjacId).orElse(null));
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setCertifikat(certifikat);
        oprema.setVlasnik(vlasnikRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);


        opremaRepository.save(oprema);

        return "redirect:/z-pregled_pojedine_opreme?opremaId=" + oprema.getId();
    }
}

