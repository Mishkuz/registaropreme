package com.HITA.bazaOpreme.Controllers;

import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.repository.OpremaRepository;
import com.HITA.bazaOpreme.service.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    ExportService service;
    @Autowired
    OpremaRepository opr;

    @GetMapping("/export")
    public void generateExcel(HttpServletResponse response, Long opremaId, HttpSession session) throws Exception {
        Oprema oprema = opr.findById(opremaId).orElse(null);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=pregled " + oprema.getNaziv() +".xls";
        response.setHeader(headerKey, headerValue);

        service.ispis(response, opremaId);

    }
}
