INSERT INTO public.kategorija (sifra,kategorija) VALUES
	 ('AO','Analitička oprema'),
	 ('MA','Mali analizatori'),
	 ('VA','Veliki analizatori'),
	 ('PO','Pomoćna oprema');
INSERT INTO public.oprema (sifra,naziv,serijski_broj,inventarski_broj,vrsta_id,proizvodjac_id,godina_proizvodnje,datum_nabave,certifikat,vlasnik_id,ups_id,datum_planiranog_servisiranja,ispravno,otpisano,datum_otpisa,napomena,interval_servisiranja_u_mjesecima) VALUES
	 ('3','5810','456','654',1,3,'1999-05-05','2000-06-06',false,1,1,'2024-07-07',true,false,NULL,NULL,12),
	 ('8','1265 JIL','555','',3,2,'2017-05-05','2000-06-18',false,1,3,'2024-11-12',true,false,NULL,NULL,12),
	 ('5','XN 1000 i','555','84632',4,1,'2003-05-05','2010-06-06',true,2,3,'2024-03-03',true,false,NULL,NULL,24),
	 ('4','CX31','5884','',2,4,'2017-04-04','2000-06-18',true,1,2,'2024-04-12',true,false,NULL,NULL,12),
	 ('11','Rotofix 32 A','123','321',1,5,'1969-05-05','2001-06-06',true,2,2,'2024-03-03',false,true,'2023-11-25',NULL,24),
	 ('8','DXC 700 AU','','5465465',5,6,'2007-04-04','2009-06-18',true,2,2,'2024-04-12',true,false,NULL,NULL,12),
	 ('2','201 E','7458456','545',6,7,'2007-04-04','2009-06-18',true,2,1,'2024-05-12',true,false,NULL,'praona, stare krvi',12);
INSERT INTO public.proizvodjac (sifra,naziv,adresa,telefon,email,kontakt_osoba) VALUES
	 ('P-3','Eppendorf','Klin City 55',NULL,NULL,NULL),
	 ('P-1','Sysmex','Špičkovina 3',NULL,NULL,NULL),
	 ('P-2','Rapidlab','Sin City 55',NULL,NULL,NULL),
	 ('P-4','Hettich','Mean City 55',NULL,NULL,NULL),
	 ('P-5','Olympus','Spin City 55',NULL,NULL,NULL),
	 ('P-6','Beckman Coulter','Queen City 55',NULL,NULL,NULL),
	 ('P-7','Končar','Knin City 55',NULL,NULL,NULL),
	 ('P-10','Sutla','Šenkovec','','',''),
	 ('P-8','Klara','Zagreb','','',''),
	 ('P-11','Vjesnik','Zagreb','','','');
INSERT INTO public.proizvodjac (sifra,naziv,adresa,telefon,email,kontakt_osoba) VALUES
	 ('P-13','Luka & jarani','Neka rupa','','','');
INSERT INTO public.radnik (sifra,ime,prezime) VALUES
	 ('R-01','Simonida','Puhalo'),
	 ('R-02','Solomon','Bičakčić'),
	 ('R-03','Doroteja','Puh');
INSERT INTO public.serviser (sifra,naziv,adresa,telefon,email,kontakt_osoba) VALUES
	 ('S-1','Grunf Technologies','Babina Greda 100',NULL,NULL,'Debeli Šef'),
	 ('S-2','Mengele Labs','Buenos Aires',NULL,NULL,'Unterprezvurštkurcšturmfirer Bujanec'),
	 ('S-3','OBZ','Zabok',NULL,NULL,'Anđelko Šu Šu');
INSERT INTO public.tip_servisa (tip_servisa) VALUES
	 ('redovni'),
	 ('umjeravanje'),
	 ('kvar');
INSERT INTO public.ups (status) VALUES
	 ('ima'),
	 ('nema'),
	 ('vlastiti');
INSERT INTO public.vlasnik (sifra,naziv,adresa,telefon,email,kontakt_osoba) VALUES
	 ('VLA-1','OBZ','Zabok',NULL,NULL,NULL),
	 ('VLA-2','Šesto Čulo d.d.','Čavoglave, Kraljevića Marka Tupsona 88',NULL,NULL,'Ante Šesto, Jozo Čulo');
INSERT INTO public.vrsta (kategorija_id,vrsta,sifra) VALUES
	 (1,'mikroskop',NULL),
	 (2,'hematološki',NULL),
	 (1,'centrifuga',NULL),
	 (2,'acidobazni',NULL),
	 (3,'biokemijski',NULL),
	 (4,'hladnjak',NULL);
