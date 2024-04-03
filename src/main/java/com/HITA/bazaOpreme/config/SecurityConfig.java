package com.HITA.bazaOpreme.config;


import com.HITA.bazaOpreme.model.Role;
import com.HITA.bazaOpreme.repository.KorisnikRepository;
import com.HITA.bazaOpreme.service.CustomKorisnikDetails;
import com.HITA.bazaOpreme.service.CustomKorisnikDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.origin.Origin;
import org.springframework.cglib.core.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;

import javax.lang.model.util.Elements;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomKorisnikDetailsService cuds;


    protected SecurityConfig(CustomKorisnikDetailsService cuds) {
        this.cuds = cuds;

    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/", "/login", "/h2-console/**",
                                        "/static/**", "/static/css/**", "/static/js/**").permitAll()
                                .requestMatchers("/templates", "/templates/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers("/pocetna").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers("/spremi_uredjenu_opremu").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")
                )
                 .headers(headers -> headers.disable())
                .formLogin(form -> form.loginPage("/login").permitAll().usernameParameter("email").passwordParameter("password").loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/pocetna") .successHandler((request, response, authentication) -> {
                    if (request.isUserInRole(Role.ADMIN.name())) {
                        response.sendRedirect("/admin/pocetna");
                    } else {
                        response.sendRedirect("/pocetna");
                    }}).permitAll()
                )
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .build();

    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cuds);

    }


}
