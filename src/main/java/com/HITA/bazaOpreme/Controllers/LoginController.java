package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.repository.KorisnikRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @GetMapping("/")
    public String signIn() {
        return "z-signIn.html";
    }


    @PostMapping("/login")
    public String processLogin(Model model, @RequestParam("email") String email, String password, HttpServletRequest request) {
       Korisnik korisnik =  korisnikRepository .findByEmailAndPassword(email, password);
        if (korisnik != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currUser", korisnik);
            return "redirect:/pocetna";
        } else {
            model.addAttribute("warningMessage", "Pogresni unos korisničkog imena i/ili lozinke!");
            return "z-signIn";
        }
    }
}
