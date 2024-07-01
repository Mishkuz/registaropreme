package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.repository.OpremaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class CertificateController {
    private OpremaRepository opremaRepository;

    @Scheduled(cron = "0 0 2 * * *")
    private void certificateControler() {
        List<Oprema> or = opremaRepository.findAll();
        LocalDate l = LocalDate.now();
        for (Oprema o : or) {
            if (o.getCertifikat() == false) {
            } else {
                if (o.getDatumPlaniranogServisiranja().isBefore(l)) {
                    o.setCertifikat(false);
                }
            }
        }
    }
}