-- public.kategorija definition

-- Drop table

-- DROP TABLE public.kategorija;

CREATE TABLE public.kategorija (
	id_kategorija bigserial NOT NULL,
	sifra varchar(20) NULL,
	kategorija varchar(50) NULL,
	CONSTRAINT kategorija_pkey PRIMARY KEY (id_kategorija)
);


-- public.proizvodjac definition

-- Drop table

-- DROP TABLE public.proizvodjac;

CREATE TABLE public.proizvodjac (
	id_proizvodjac bigserial NOT NULL,
	sifra varchar(20) NOT NULL,
	naziv varchar(50) NOT NULL,
	adresa varchar(50) NOT NULL,
	telefon varchar(20) NULL,
	email varchar(50) NULL,
	kontakt_osoba varchar(50) NULL,
	CONSTRAINT proizvodjac_pkey PRIMARY KEY (id_proizvodjac),
	CONSTRAINT proizvodjac_unique UNIQUE (sifra)
);


-- public.radnik definition

-- Drop table

-- DROP TABLE public.radnik;

CREATE TABLE public.radnik (
	id_radnik bigserial NOT NULL,
	sifra varchar(20) NOT NULL,
	ime varchar(50) NOT NULL,
	prezime varchar(50) NOT NULL,
	CONSTRAINT radnik_pkey PRIMARY KEY (id_radnik)
);


-- public.serviser definition

-- Drop table

-- DROP TABLE public.serviser;

CREATE TABLE public.serviser (
	id_serviser bigserial NOT NULL,
	sifra varchar(20) NOT NULL,
	naziv varchar(50) NOT NULL,
	adresa varchar(100) NOT NULL,
	telefon varchar(20) NULL,
	email varchar(50) NULL,
	kontakt_osoba varchar(50) NULL,
	CONSTRAINT serviser_pkey PRIMARY KEY (id_serviser)
);


-- public.tip_servisa definition

-- Drop table

-- DROP TABLE public.tip_servisa;

CREATE TABLE public.tip_servisa (
	id_tip_servisa bigserial NOT NULL,
	tip_servisa varchar(50) NOT NULL,
	CONSTRAINT tip_servisa_pkey PRIMARY KEY (id_tip_servisa)
);


-- public.ups definition

-- Drop table

-- DROP TABLE public.ups;

CREATE TABLE public.ups (
	id_ups bigserial NOT NULL,
	status varchar(50) NOT NULL,
	CONSTRAINT ups_pkey PRIMARY KEY (id_ups)
);


-- public.vlasnik definition

-- Drop table

-- DROP TABLE public.vlasnik;

CREATE TABLE public.vlasnik (
	id_vlasnik bigserial NOT NULL,
	sifra varchar(20) NOT NULL,
	naziv varchar(50) NOT NULL,
	adresa varchar(100) NOT NULL,
	telefon varchar(20) NULL,
	email varchar(50) NULL,
	kontakt_osoba varchar(50) NULL,
	CONSTRAINT vlasnik_pkey PRIMARY KEY (id_vlasnik)
);


-- public.vrsta definition

-- Drop table

-- DROP TABLE public.vrsta;

CREATE TABLE public.vrsta (
	id_vrsta bigserial NOT NULL,
	kategorija_id int8 NULL,
	vrsta varchar(50) NULL,
	sifra varchar(20) NULL,
	CONSTRAINT vrsta_pkey PRIMARY KEY (id_vrsta),
	CONSTRAINT vrsta_unique UNIQUE (vrsta, sifra),
	CONSTRAINT vrsta_kategorija_id_fkey FOREIGN KEY (kategorija_id) REFERENCES public.kategorija(id_kategorija)
);


-- public.oprema definition

-- Drop table

-- DROP TABLE public.oprema;

CREATE TABLE public.oprema (
	id_oprema bigserial NOT NULL,
	sifra varchar(20) NOT NULL,
	naziv varchar(100) NOT NULL,
	serijski_broj varchar(20) NULL,
	inventarski_broj varchar(20) NULL,
	vrsta_id int8 NOT NULL,
	proizvodjac_id int8 NOT NULL,
	godina_proizvodnje date NULL,
	datum_nabave date NULL,
	certifikat bool NOT NULL DEFAULT false,
	vlasnik_id int8 NOT NULL,
	ups_id int8 NOT NULL,
	datum_planiranog_servisiranja date NULL,
	ispravno bool NOT NULL DEFAULT true,
	otpisano bool NULL,
	datum_otpisa date NULL,
	napomena varchar(256) NULL,
	interval_servisiranja_u_mjesecima int4 NULL,
	CONSTRAINT oprema_pkey PRIMARY KEY (id_oprema),
	CONSTRAINT fk_proizvodjac FOREIGN KEY (proizvodjac_id) REFERENCES public.proizvodjac(id_proizvodjac),
	CONSTRAINT fk_ups FOREIGN KEY (ups_id) REFERENCES public.ups(id_ups),
	CONSTRAINT fk_vlasnik FOREIGN KEY (vlasnik_id) REFERENCES public.vlasnik(id_vlasnik),
	CONSTRAINT fk_vrsta FOREIGN KEY (vrsta_id) REFERENCES public.vrsta(id_vrsta)
);


-- public.odrzavanje definition

-- Drop table

-- DROP TABLE public.odrzavanje;

CREATE TABLE public.odrzavanje (
	id_odrzavanje bigserial NOT NULL,
	radnik_id int8 NOT NULL,
	oprema_id int8 NOT NULL,
	tip_servisa_id int8 NOT NULL,
	serviser_id int8 NOT NULL,
	opis_kvara varchar(512) NULL,
	datum_prijave date NULL,
	datum_otpreme date NULL,
	datum_povrata date NULL,
	datum_planiranog_servisiranja date NULL,
	CONSTRAINT odrzavanje_pkey PRIMARY KEY (id_odrzavanje),
	CONSTRAINT fk_oprema FOREIGN KEY (oprema_id) REFERENCES public.oprema(id_oprema),
	CONSTRAINT fk_radnik FOREIGN KEY (radnik_id) REFERENCES public.radnik(id_radnik),
	CONSTRAINT fk_serviser FOREIGN KEY (serviser_id) REFERENCES public.serviser(id_serviser),
	CONSTRAINT fk_tip_servisa FOREIGN KEY (tip_servisa_id) REFERENCES public.tip_servisa(id_tip_servisa)
);