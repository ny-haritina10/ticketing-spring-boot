CREATE TABLE admins (
	id bigserial NOT NULL,
	email varchar(255) NOT NULL,
	password_hash varchar(255) NOT NULL,
	CONSTRAINT admins_email_key UNIQUE (email),
	CONSTRAINT admins_pkey PRIMARY KEY (id)
);

CREATE TABLE categorie_age (
	id serial4 NOT NULL,
	"label" varchar(255) NOT NULL,
	CONSTRAINT categorie_age_pkey PRIMARY KEY (id)
);

CREATE TABLE cities (
	id serial4 NOT NULL,
	city_name varchar(100) NOT NULL,
	country varchar(100) NOT NULL,
	CONSTRAINT cities_city_name_country_key UNIQUE (city_name, country),
	CONSTRAINT cities_pkey PRIMARY KEY (id)
);

CREATE TABLE clients (
	id serial4 NOT NULL,
	"name" varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	password_hash varchar(255) NOT NULL,
	passport_image varchar(255) NULL,
	CONSTRAINT clients_email_key UNIQUE (email),
	CONSTRAINT clients_pkey PRIMARY KEY (id)
);

CREATE TABLE planes (
	id serial4 NOT NULL,
	model_name varchar(100) NOT NULL,
	fabrication_date date NOT NULL,
	CONSTRAINT planes_pkey PRIMARY KEY (id)
);

CREATE TABLE flights (
	id serial4 NOT NULL,
	flight_number varchar(20) NOT NULL,
	id_plane int4 NULL,
	id_origin_city int4 NULL,
	id_destination_city int4 NULL,
	departure_time timestamptz NOT NULL,
	arrival_time timestamptz NOT NULL,
	reservation_deadline_hours int4 DEFAULT 3 NOT NULL,
	cancellation_deadline_hours int4 DEFAULT 24 NOT NULL,
	CONSTRAINT flights_cancellation_deadline_hours_check CHECK ((cancellation_deadline_hours >= 0)),
	CONSTRAINT flights_check CHECK ((id_origin_city <> id_destination_city)),
	CONSTRAINT flights_check1 CHECK ((arrival_time > departure_time)),
	CONSTRAINT flights_flight_number_key UNIQUE (flight_number),
	CONSTRAINT flights_pkey PRIMARY KEY (id),
	CONSTRAINT flights_reservation_deadline_hours_check CHECK ((reservation_deadline_hours >= 0)),
	CONSTRAINT flights_id_destination_city_fkey FOREIGN KEY (id_destination_city) REFERENCES cities(id) ON DELETE RESTRICT,
	CONSTRAINT flights_id_origin_city_fkey FOREIGN KEY (id_origin_city) REFERENCES cities(id) ON DELETE RESTRICT,
	CONSTRAINT flights_id_plane_fkey FOREIGN KEY (id_plane) REFERENCES planes(id) ON DELETE RESTRICT
);

CREATE TABLE percentage_discount (
	id bigserial NOT NULL,
	id_categorie_age int4 NOT NULL,
	age_max int4 NOT NULL,
	percentage_discount float4 NULL,
	CONSTRAINT percentage_discount_pkey PRIMARY KEY (id),
	CONSTRAINT percentage_discount_id_categorie_age_fkey FOREIGN KEY (id_categorie_age) REFERENCES categorie_age(id)
);

CREATE TABLE reservations (
	id serial4 NOT NULL,
	id_client int4 NULL,
	id_flight int4 NULL,
	reservation_time timestamptz DEFAULT CURRENT_TIMESTAMP NULL,
	nbr_billet_total int4 NOT NULL,
	nbr_billet_enfant int4 NOT NULL,
	nbr_billet_adulte int4 NOT NULL,
	CONSTRAINT reservations_pkey PRIMARY KEY (id),
	CONSTRAINT reservations_id_client_fkey FOREIGN KEY (id_client) REFERENCES clients(id) ON DELETE RESTRICT,
	CONSTRAINT reservations_id_flight_fkey FOREIGN KEY (id_flight) REFERENCES flights(id) ON DELETE RESTRICT
);

CREATE TABLE reservations_details (
	id serial4 NOT NULL,
	id_reservation int4 NULL,
	seat_category varchar(255) NOT NULL,
	name_voyageur varchar(255) NOT NULL,
	dtn_voyageur date NOT NULL,
	passport_image varchar(255) NOT NULL,
	price numeric(10, 2) NOT NULL,
	is_promotional bool NOT NULL,
	is_cancel bool NOT NULL,
	cancellation_time timestamptz NULL,
	CONSTRAINT reservations_details_pkey PRIMARY KEY (id),
	CONSTRAINT reservations_details_id_reservation_fkey FOREIGN KEY (id_reservation) REFERENCES reservations(id) ON DELETE RESTRICT
);

CREATE TABLE seat_configurations (
	id serial4 NOT NULL,
	id_plane int4 NULL,
	category varchar(255) NOT NULL,
	number_of_seats int4 NOT NULL,
	CONSTRAINT seat_configurations_id_plane_category_key UNIQUE (id_plane, category),
	CONSTRAINT seat_configurations_number_of_seats_check CHECK ((number_of_seats > 0)),
	CONSTRAINT seat_configurations_pkey PRIMARY KEY (id),
	CONSTRAINT seat_configurations_id_plane_fkey FOREIGN KEY (id_plane) REFERENCES planes(id) ON DELETE CASCADE
);

CREATE TABLE flight_prices (
	id serial4 NOT NULL,
	id_flight int4 NULL,
	category varchar(255) NOT NULL,
	base_price numeric(10, 2) NOT NULL,
	CONSTRAINT flight_prices_base_price_check CHECK ((base_price > (0)::numeric)),
	CONSTRAINT flight_prices_id_flight_category_key UNIQUE (id_flight, category),
	CONSTRAINT flight_prices_pkey PRIMARY KEY (id),
	CONSTRAINT flight_prices_id_flight_fkey FOREIGN KEY (id_flight) REFERENCES flights(id) ON DELETE CASCADE
);

CREATE TABLE flight_promotions (
	id serial4 NOT NULL,
	id_flight int4 NULL,
	category varchar(255) NOT NULL,
	discount_percentage numeric(5, 2) NOT NULL,
	seats_available int4 NOT NULL,
	CONSTRAINT flight_promotions_discount_percentage_check CHECK (((discount_percentage > (0)::numeric) AND (discount_percentage <= (100)::numeric))),
	CONSTRAINT flight_promotions_id_flight_category_key UNIQUE (id_flight, category),
	CONSTRAINT flight_promotions_pkey PRIMARY KEY (id),
	CONSTRAINT flight_promotions_seats_available_check CHECK ((seats_available > 0)),
	CONSTRAINT flight_promotions_id_flight_fkey FOREIGN KEY (id_flight) REFERENCES flights(id) ON DELETE CASCADE
);

CREATE TABLE flight_reservation (
	id serial4 NOT NULL,
	id_flight int4 NULL,
	reservation_hour_allowed int4 NOT NULL,
	annulation_hour_allowed int4 NOT NULL,
	CONSTRAINT flight_reservation_pkey PRIMARY KEY (id),
	CONSTRAINT flight_reservation_id_flight_fkey FOREIGN KEY (id_flight) REFERENCES flights(id) ON DELETE RESTRICT
);