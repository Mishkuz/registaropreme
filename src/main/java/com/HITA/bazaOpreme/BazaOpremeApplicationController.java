package com.HITA.bazaOpreme;
import com.HITA.bazaOpreme.model.*;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Controller
public class BazaOpremeApplicationController {
    @Autowired
    OpremaRepository opremaRepository;
    @Autowired
    KvarRepository kvarRepository;
    @Autowired
    KategorijaRepository kategorijaRepository;
    @Autowired
    OdrzavanjeRepository odrzavanjeRepository;
    @Autowired
    VrstaRepository vrstaRepository;
    @Autowired
    private TvrtkaRepository tvrtkaRepository;
    @GetMapping("/pocetna")
    public String pocetna(Model model) {
        List<Oprema> opremaList = opremaRepository.findAll();
        model.addAttribute("opremaList", opremaList);
        model.addAttribute(opremaRepository.findAll());
        return "pocetna.html";
    }
    @GetMapping("/unos_novog_kvara")
    public String unos_novog_kvara(Model model, Long opremaId) {
        List<Oprema> opremaList = opremaRepository.findAll();
        List<Kvar> kvarList = kvarRepository.findAll();
        model.addAttribute(kvarList);
        model.addAttribute(opremaList);
        model.addAttribute(opremaRepository.findById(opremaId).get());
        return "unos_novog_kvara.html";
    }
    @PostMapping("/spremiuredaj")
    public String spremiUredaj(@RequestParam("sifra") String sifra,
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
                               Model model) {
        // Stvaranje instance Oprema objekta
        Oprema oprema = new Oprema();
        oprema.setSifra(sifra);
        oprema.setNaziv(naziv);
        oprema.setSerijskiBroj(serijskiBroj);
        oprema.setInventarskiBroj(inventarskiBroj);
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setVrsta(vrstaRepository.findById(vrstaId).orElse(null));
        oprema.setTvrtkaProizvodjac(tvrtkaRepository.findById(proizvodjacId).orElse(null));
        oprema.setKategorija(kategorijaRepository.findById(kategorijaId).orElse(null));
        oprema.setGodinaProizvodnje(godinaProizvodnje);
        oprema.setDatumNabave(datumNabave);
        oprema.setCertifikat(certifikat);
        oprema.setTvrtkaVlasnik(tvrtkaRepository.findById(vlasnikId).orElse(null));
        oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        opremaRepository.save(oprema);
        return "redirect:/pocetna";
    }
    @GetMapping("/dodavanjenovoguredaja")
    public String dodavanjenovoguredaja() {
        return "unos_nove_opreme.html";
    }
    @GetMapping("/unosnoveopreme")
    public String unosnoveopreme(Model model) {
        List<Kategorija> kategorije = kategorijaRepository.findAll();
        List<Vrsta> vrste = vrstaRepository.findAll();
        List<Tvrtka> proizvodjaci = tvrtkaRepository.findAll();
        List<Tvrtka> vlasnici = tvrtkaRepository.findAll();
        model.addAttribute("kategorije", kategorije);
        model.addAttribute("vrste", vrste);
        model.addAttribute("proizvodjaci", proizvodjaci);
        model.addAttribute("vlasnici", vlasnici);
        return "unos_nove_opreme.html";
    }
    @GetMapping("/oprema_kvarovi")
    public String kvarovi(Model model) {
        model.addAttribute(kvarRepository.findAll());
        model.addAttribute(opremaRepository.findAll());
        return "oprema_kvarovi.html";
    }
    @GetMapping("/evidencijaodrzavanja")
    public String evidencijaodrzavanja(Model model) {
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findAll();
        model.addAttribute("odrzavanjeList", odrzavanjeList);
        return "evidencijaodrzavanja.html";
    }
    @GetMapping("/spremiKvar")
    public String spremiKvar(//@RequestParam(“sifra”) String sifra,
                             //@RequestParam(“naziv”) String naziv,
                             //@RequestParam(“serijskiBroj”) String serijskiBroj,
                             //@RequestParam(“inventarskiBroj”) String inventarskiBroj,
                             //@RequestParam(“kategorija”) Long kategorijaId,
                             //@RequestParam(“vrsta”) Long vrstaId,
                             //@RequestParam(“oprema”) Long id,
                             @RequestParam("prijavioRadnik") String prijavioRadnik,
                             @RequestParam("opisKvara") String opisKvara,
                             @RequestParam("datumPrijave") String datumPrijave,
                             //@RequestParam(“godinaProizvodnje”) LocalDate godinaProizvodnje,
                             // @RequestParam(“datumNabave”) LocalDate datumNabave,
                             //@RequestParam(“certifikat”) boolean certifikat,
                             // @RequestParam(“intervalServisiranjaUMjesecima”) Integer intervalServisiranjaUMjesecima,
                             // @RequestParam(“datumPlaniranogServisiranja”) LocalDate datumPlaniranogServisiranja,
                             Model model,
                             HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        // Stvaranje instance Kvara objekta
        Kvar kvar = new Kvar(prijavioRadnik, opisKvara, null);
        kvar.setPrijavioRadnik(prijavioRadnik);
        kvar.setOpisKvara(opisKvara);
        //kvar.setDatumPrijave(datumPrijave);
        kvar.setDatumPrijave(new Date());
        //Oprema oprema = opremaRepository.findById(id).orElse(null);
        // oprema.setSifra(sifra);
        //oprema.setNaziv(naziv);
        //oprema.setSerijskiBroj(serijskiBroj);
        // oprema.setInventarskiBroj(inventarskiBroj);
        // Kategorija kategorija = kategorijaRepository.findById(kategorijaId).orElse(null);
        // oprema.setKategorija(kategorija);
        //Vrsta vrsta = vrstaRepository.findById(vrstaId).orElse(null);
        //oprema.setVrsta(vrsta);
        // oprema.setGodinaProizvodnje(godinaProizvodnje);
        //oprema.setDatumNabave(datumNabave);
        //oprema.setCertifikat(certifikat);
        //oprema.setIntervalServisiranjaUMjesecima(intervalServisiranjaUMjesecima);
        //oprema.setDatumPlaniranogServisiranja(datumPlaniranogServisiranja);
        kvarRepository.save(kvar);
        return "redirect:/oprema_kvarovi";
    }
    @GetMapping("/pokaziKvarove")
    public String showFailures(Model model) {
        model.addAttribute(kvarRepository.findAll());
        return "oprema_kvarovi.html";
    }
}