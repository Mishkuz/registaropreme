package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.repository.KorisnikRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class LoginController {


    private final KorisnikRepository korisnikRepository;

    @GetMapping("/")
    public String signIn() {
        return "z-signIn.html";
    }

    @GetMapping("/login")
    public String signn() {
        return "z-signIn.html";
    }


    @PostMapping("/login")
    public String processLogin(Model model, @RequestParam("email") String email, String password, HttpSession session) {
        Korisnik korisnik = korisnikRepository.findByEmailAndPassword(email, password);
        if (korisnik != null) {
            session.setAttribute("currUser", korisnik);
            return "redirect:/pocetna";
        } else {
            model.addAttribute("warningMessage", "Pogresni unos korisničkog imena i/ili lozinke!");
            return "z-signIn";
        }
    }

}