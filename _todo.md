- multiple reservations
    - 3 enfants
    - 4 adultes

- NUM UNIQUE reservations
- details reservations
    - num unique FK reservations 

- Reservations
    . id 
    . id_client (who made the reservation)
    . id_flight 
    . reservation_time
    . nbr_billet_total
    . nbr_billet_enfant
    . nbr_billet_adulte

- ReservationsDetail
    . id
    . id_reservation
    . id_seat_categorie
    . name 
    . dtn
    . passeport_image
    . price (calculated: base_price , seat_discount_percentage, age_discount_percentage)
    . is_promotional
    . is_cancel
    . cancellation_time