CREATE TABLE categorie_age (
	id serial NOT NULL,
	"label" varchar(255) NOT NULL,
	CONSTRAINT categorie_age_pkey PRIMARY KEY (id)
);

CREATE TABLE percentage_discount (
	id serial NOT NULL,
	id_categorie_age int NOT NULL,
	age_max int NOT NULL,
	percentage_discount float NULL,
	CONSTRAINT percentage_discount_pkey PRIMARY KEY (id),
	CONSTRAINT percentage_discount_id_categorie_age_fkey FOREIGN KEY (id_categorie_age) REFERENCES public.categorie_age(id)
);