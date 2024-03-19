package com.HITA.bazaOpreme.service;

import com.HITA.bazaOpreme.model.Korisnik;
import com.HITA.bazaOpreme.model.Kvar;
import com.HITA.bazaOpreme.model.Odrzavanje;
import com.HITA.bazaOpreme.model.Oprema;
import com.HITA.bazaOpreme.repository.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;


@AllArgsConstructor
@Service
public class ExportService {
    private final OpremaRepository opremaRepository;
    private final KvarRepository kvarRepository;

    private final OdrzavanjeRepository odrzavanjeRepository;


    private final String servisS = "Servis";
    private final String servisIzvanredanS = "Izvanredan servis";
    private final String umjeravanjeS = "umjeravanje";
    @Autowired
    private KorisnikRepository korisnikRepository;


    public void ispis(HttpServletResponse response, Long opremaId) throws IOException {
        Oprema oprema = opremaRepository.findById(opremaId).orElse(null);
        List<Odrzavanje> odrzavanjeList = odrzavanjeRepository.findByOpremaAndTip(oprema, umjeravanjeS);
        List<Odrzavanje> odrzavanjeSList = odrzavanjeRepository.findByOpremaAndTipOrOpremaAndTip(oprema, servisS, oprema, servisIzvanredanS);
        List<Kvar> kvarList = kvarRepository.findByOprema(oprema);
        odrzavanjeList.sort(Comparator.comparing(Odrzavanje::getDatumUmjeravanja));
        odrzavanjeSList.sort(Comparator.comparing(Odrzavanje::getDatumOtpreme));
        kvarList.sort(Comparator.comparing(Kvar::getDatumPrijave));

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("PregledPojedineOpreme");

        HSSFRow row = sheet.createRow(0);

        // Formatiranje datuma
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy")); // Prilagodite formatu datuma prema potrebi
        // Bojanje ćelija
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCell cel = row.createCell(0);
        cel.setCellValue("Pregled opreme");
        cel.setCellStyle(style);
        row.createCell(1).setCellValue(oprema.getNaziv());

        HSSFRow p = sheet.createRow(3);
        HSSFCell celll = p.createCell(0);
        celll.setCellValue("Proizvođač: ");
        celll.setCellStyle(style);
        p.createCell(1).setCellValue(oprema.getProizvodjac().getNaziv());

        HSSFRow dn = sheet.createRow(4);
       // HSSFCell celi
        dn.createCell(0).setCellValue("Datum nabave: ");


        Cell dateCell = dn.createCell(1);
        dateCell.setCellValue(oprema.getDatumNabave());
        dateCell.setCellStyle(dateCellStyle);

        HSSFRow gp = sheet.createRow(5);
        gp.createCell(0).setCellValue("Godina proizvodnje: ");
        HSSFCell godinaProizvodnjeCell = gp.createCell(1);
        godinaProizvodnjeCell.setCellValue(oprema.getGodinaProizvodnje());
        godinaProizvodnjeCell.setCellStyle(dateCellStyle);

        HSSFRow sb = sheet.createRow(6);
        sb.createCell(0).setCellValue("Serijski broj: ");
        sb.createCell(1).setCellValue(oprema.getSerijskiBroj());

        HSSFRow ib = sheet.createRow(7);
        ib.createCell(0).setCellValue("Inventarski broj: ");
        ib.createCell(1).setCellValue(oprema.getInventarskiBroj());

        HSSFRow si = sheet.createRow(8);
        si.createCell(0).setCellValue("Šifra: ");
        si.createCell(1).setCellValue(oprema.getSifra());

        HSSFRow k = sheet.createRow(9);
        k.createCell(0).setCellValue("Kategorija: ");
        k.createCell(1).setCellValue(oprema.getKategorija().getKategorija());

        HSSFRow v = sheet.createRow(10);
        v.createCell(0).setCellValue("Vrsta: ");
        v.createCell(1).setCellValue(oprema.getVrsta().getVrsta());


        HSSFRow c = sheet.createRow(11);
        c.createCell(0).setCellValue("Certifikat: ");
        c.createCell(1).setCellValue(oprema.getCertifikat());

        HSSFRow isp = sheet.createRow(12);
        isp.createCell(0).setCellValue("Ispravno: ");
        isp.createCell(1).setCellValue(oprema.getIspravno());

        HSSFRow ups = sheet.createRow(13);
        ups.createCell(0).setCellValue("UPS: ");
        ups.createCell(1).setCellValue(oprema.getUps());


        HSSFRow dps = sheet.createRow(14);
        dps.createCell(0).setCellValue("Datum planiranog servisiranja: ");
        HSSFCell datumPlaniranogServisiranjaCell = dps.createCell(1);
        datumPlaniranogServisiranjaCell.setCellValue(oprema.getDatumPlaniranogServisiranja());
        datumPlaniranogServisiranjaCell.setCellStyle(dateCellStyle);

        HSSFRow vla = sheet.createRow(15);
        vla.createCell(0).setCellValue("Vlasnik: ");
        vla.createCell(1).setCellValue(oprema.getVlasnik().getNaziv());

        HSSFRow rad = sheet.createRow(16);
        rad.createCell(0).setCellValue("Radilište: ");
        rad.createCell(1).setCellValue(oprema.getRadiliste().getImeRadilista());

        HSSFRow isum = sheet.createRow(17);
        isum.createCell(0).setCellValue("Interval servisiranja u mjesecima: ");
        isum.createCell(1).setCellValue(oprema.getIntervalServisiranjaUMjesecima());

        HSSFRow dot = sheet.createRow(18);
        dot.createCell(0).setCellValue("Datum otpisa: ");
        HSSFCell datumOtpisaCell = dot.createCell(1);
        datumOtpisaCell.setCellValue(oprema.getDatumOtpisa());
        datumOtpisaCell.setCellStyle(dateCellStyle);
        int dRI = 25;


        HSSFRow hu = sheet.createRow(dRI + 1);
        hu.createCell(0).setCellValue("Umjeravanja ");
        dRI++;
        dRI++;

        HSSFRow hss = sheet.createRow(dRI);
        hss.createCell(0).setCellValue("Datum otpreme: ");
        hss.createCell(1).setCellValue("Datum povrata: ");
        hss.createCell(2).setCellValue("Serviser: ");
        hss.createCell(3).setCellValue("Radnik: ");
        dRI++;


        for (Odrzavanje odrzavanje : odrzavanjeList) {
            HSSFRow dataRow = sheet.createRow(dRI);


            dataRow.createCell(0).setCellValue(odrzavanje.getDatumOtpreme());
            dataRow.createCell(1).setCellValue(odrzavanje.getDatumPovrata());

            dataRow.getCell(0).setCellStyle(dateCellStyle);
            dataRow.getCell(1).setCellStyle(dateCellStyle);
            dataRow.createCell(2).setCellValue(odrzavanje.getServiser().getNaziv());
            dataRow.createCell(3).setCellValue(odrzavanje.getRadnik());
            dRI++;

        }
        HSSFRow h = sheet.createRow(dRI + 1);
        h.createCell(0).setCellValue("Seervisi: ");
        dRI++;
        dRI++;

        HSSFRow hs = sheet.createRow(dRI);
        hs.createCell(0).setCellValue("Datum otpreme: ");
        hs.createCell(1).setCellValue("Datum povrata: ");
        hs.createCell(2).setCellValue("Datum planiranog servisa: ");
        hs.createCell(3).setCellValue("Tip servisa: ");
        hs.createCell(4).setCellValue("Serviser: ");
        hs.createCell(5).setCellValue("Radnik: ");
        hs.createCell(6).setCellValue("Opis: ");
        dRI++;

        for (Odrzavanje odrzavanje : odrzavanjeSList) {
            HSSFRow dataRow = sheet.createRow(dRI);

            dataRow.createCell(0).setCellValue(odrzavanje.getDatumOtpreme());
            dataRow.createCell(1).setCellValue(odrzavanje.getDatumPovrata());
            dataRow.createCell(2).setCellValue(odrzavanje.getDatumPlaniranogServisiranja());

            dataRow.getCell(0).setCellStyle(dateCellStyle);
            dataRow.getCell(1).setCellStyle(dateCellStyle);
            dataRow.getCell(2).setCellStyle(dateCellStyle);
            dataRow.createCell(3).setCellValue(odrzavanje.getTip());
            dataRow.createCell(4).setCellValue(odrzavanje.getServiser().getNaziv());
            dataRow.createCell(5).setCellValue(odrzavanje.getRadnik());
            dataRow.createCell(6).setCellValue(odrzavanje.getOpisOdrzavanja());
            dRI++;

        }

        HSSFRow hk = sheet.createRow(dRI + 1);
        hk.createCell(0).setCellValue("Kvarovi: ");
        dRI++;
        dRI++;


        HSSFRow d3 = sheet.createRow(dRI);
        d3.createCell(0).setCellValue("Datum prijave: ");
        d3.createCell(1).setCellValue("Prijavio radnik: ");
        d3.createCell(2).setCellValue("Opis kvara: ");
        dRI++;


        for (Kvar kvar : kvarList) {
            HSSFRow dataRow = sheet.createRow(dRI);


            dataRow.createCell(0).setCellValue(kvar.getDatumPrijave());
            dataRow.getCell(0).setCellStyle(dateCellStyle);
            dataRow.createCell(1).setCellValue(kvar.getPrijavioRadnik());
            dataRow.createCell(2).setCellValue(kvar.getOpisKvara());
            dRI++;

        }

        int[] columnWidths = new int[20]; // Promijenite 20 na broj kolona koje imate
        for (int i = 0; i <= dRI; i++) {
            HSSFRow r = sheet.getRow(i);
            if (r != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell != null) {
                        int contentLength = cell.getStringCellValue().length(); // Dobivanje duljine sadržaja ćelije
                        // Ako je duljina sadržaja veća od maksimalne širine za ovu kolonu, ažurirajte maksimalnu širinu
                        if (contentLength * 256 > columnWidths[j]) {
                            columnWidths[j] = contentLength * 256; // Ažuriranje maksimalne širine za ovu kolonu
                        }
                    }
                }
            }
        }

        // Prolazak kroz redove kako bi se postavila širina kolone na maksimalnu širinu
        for (int i = 0; i <= dRI; i++) {
            HSSFRow r = sheet.getRow(i);
            if (r != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell != null)
                        // Postavljanje širine kolone na maksimalnu širinu za tu kolonu
                        sheet.setColumnWidth(j, columnWidths[j]);
                }
            }
        }


        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

}
