-- Planes table
CREATE TABLE planes (
    id SERIAL PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    fabrication_date DATE NOT NULL
);

-- Seat configuration per plane
CREATE TABLE seat_configurations (
    id SERIAL PRIMARY KEY,
    id_plane INTEGER REFERENCES planes(id) ON DELETE CASCADE,
    category seat_category NOT NULL,
    number_of_seats INTEGER NOT NULL CHECK (number_of_seats > 0),
    UNIQUE(id_plane, category)
);

-- Cities table
CREATE TABLE cities (
    id SERIAL PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    UNIQUE(city_name, country)
);

-- Flights table
CREATE TABLE flights (
    id SERIAL PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    id_plane INTEGER REFERENCES planes(id) ON DELETE RESTRICT,
    id_origin_city INTEGER REFERENCES cities(id) ON DELETE RESTRICT,
    id_destination_city INTEGER REFERENCES cities(id) ON DELETE RESTRICT,
    departure_time TIMESTAMP WITH TIME ZONE NOT NULL,
    arrival_time TIMESTAMP WITH TIME ZONE NOT NULL,
    reservation_deadline_hours INTEGER NOT NULL DEFAULT 3,
    cancellation_deadline_hours INTEGER NOT NULL DEFAULT 24,
    CHECK (id_origin_city != id_destination_city),
    CHECK (arrival_time > departure_time),
    CHECK (reservation_deadline_hours >= 0),
    CHECK (cancellation_deadline_hours >= 0)
);

-- Seat prices for each flight by category
CREATE TABLE flight_prices (
    id SERIAL PRIMARY KEY,
    id_flight INTEGER REFERENCES flights(id) ON DELETE CASCADE,
    category seat_category NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL CHECK (base_price > 0),
    UNIQUE(id_flight, category)
);

-- Clients table
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Admins table
CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Flight promotions table
CREATE TABLE flight_promotions (
    id SERIAL PRIMARY KEY,
    id_flight INTEGER REFERENCES flights(id) ON DELETE CASCADE,
    category seat_category NOT NULL,
    discount_percentage DECIMAL(5, 2) NOT NULL CHECK (discount_percentage > 0 AND discount_percentage <= 100),
    seats_available INTEGER NOT NULL CHECK (seats_available > 0),
    UNIQUE(id_flight, category)
);

-- Reservations table
CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    id_client INTEGER REFERENCES clients(id) ON DELETE RESTRICT,
    id_flight INTEGER REFERENCES flights(id) ON DELETE RESTRICT,
    seat_category seat_category NOT NULL,
    is_promotional BOOLEAN NOT NULL DEFAULT FALSE,
    price_paid DECIMAL(10, 2) NOT NULL CHECK (price_paid >= 0),
    reservation_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_cancelled BOOLEAN NOT NULL DEFAULT FALSE,
    cancellation_time TIMESTAMP WITH TIME ZONE,
    CHECK (cancellation_time IS NULL OR cancellation_time > reservation_time)
);

-- Static Data
INSERT INTO admins (id, email, password_hash) 
VALUES (1, 'admin@gmail.com', 'admin');

INSERT INTO planes (id, model_name, fabrication_date)
VALUES (1, 'Boeing 513', '2010-01-01');

INSERT INTO planes (id, model_name, fabrication_date)
VALUES (2, 'Airbus A320', '2015-06-01');

INSERT INTO planes (id, model_name, fabrication_date)
VALUES (3, 'Boeing 787 Dreamliner', '2012-03-15');

INSERT INTO planes (id, model_name, fabrication_date)
VALUES (4, 'Bombardier CRJ900', '2008-09-20');

INSERT INTO planes (id, model_name, fabrication_date)
VALUES (5, 'Embraer ERJ145', '2006-11-10');

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (1, 1, 'Economy', 75);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (2, 1, 'Business', 45);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (3, 1, 'First Class', 25);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (4, 2, 'Economy', 100);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (5, 2, 'Business', 50);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (6, 2, 'First Class', 30);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (7, 3, 'Economy', 100);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (8, 3, 'Business', 50);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (9, 3, 'First Class', 30);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (10, 4, 'Economy', 100);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (11, 4, 'Business', 50);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (12, 4, 'First Class', 30);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (13, 5, 'Economy', 100);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (14, 5, 'Business', 50);

INSERT INTO seat_configurations (id, id_plane, category, number_of_seats)
VALUES (15, 5, 'First Class', 30);

INSERT INTO cities (id, city_name, country)
VALUES (1, 'New York', 'USA');

INSERT INTO cities (id, city_name, country)
VALUES (2, 'London', 'United Kingdom');

INSERT INTO cities (id, city_name, country)
VALUES (3, 'Paris', 'France');

INSERT INTO cities (id, city_name, country)
VALUES (4, 'Tokyo', 'Japan');

INSERT INTO cities (id, city_name, country)
VALUES (5, 'Sydney', 'Australia');

INSERT INTO cities (id, city_name, country)
VALUES (6, 'Beijing', 'China');

INSERT INTO cities (id, city_name, country)
VALUES (7,'Mumbai','India');

INSERT INTO cities (id ,city_name ,country )
VALUES(8,'Cairo','Egypt' );

INSERT INTO cities(id ,city_name ,country )
VALUES(9,'São Paulo','Brazil' );

INSERT INTO cities(id ,city_name ,country )
VALUES(10,'Moscow','Russia');


-- Alter category column in seat_configurations table
ALTER TABLE seat_configurations 
ALTER COLUMN category TYPE VARCHAR(255) USING category::VARCHAR(255);

-- Alter category column in flight_prices table
ALTER TABLE flight_prices 
ALTER COLUMN category TYPE VARCHAR(255) USING category::VARCHAR(255);

-- Alter category column in flight_promotions table
ALTER TABLE flight_promotions 
ALTER COLUMN category TYPE VARCHAR(255) USING category::VARCHAR(255);

-- Alter seat_category column in reservations table
ALTER TABLE reservations 
ALTER COLUMN seat_category TYPE VARCHAR(255) USING seat_category::VARCHAR(255);

-- 
INSERT INTO clients (id, name, email, password_hash)
VALUES (1, 'Client 1', 'client1@gmail.com', 'client1');

INSERT INTO clients (id, name, email, password_hash)
VALUES (2, 'Client 2', 'client2@gmail.com', 'client2');

INSERT INTO clients (id, name, email, password_hash)
VALUES (3, 'Client 3', 'client3@gmail.com', 'client3');

CREATE TABLE flight_reservation (
    id SERIAL PRIMARY KEY,
    id_flight INTEGER REFERENCES flights(id) ON DELETE RESTRICT,
    reservation_hour_allowed INTEGER NOT NULL,
    annulation_hour_allowed INTEGER NOT NULL  
);

ALTER TABLE clients ADD COLUMN passport_image VARCHAR(255);