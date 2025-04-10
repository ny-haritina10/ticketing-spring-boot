- calculate price automatically
    - getBaseSeatPrice(flight, seatCategory)
    - isSeatPromotional(flight, seatCategory)
    - calcutalePromotionalReduction(flight, seatCategory)
        - getBasePrice
        - getPromotionalReduction
        - proceed calculate

/* 09-04-25 ========= */

+ Web-Service
    - EXPORT PDF
        - Ticketing in Spring Boot
            => create a web service to export into PDF format the reservations and the reservations details
            => takes an ID reservations
            => Export the reservations and reservations details into PDF
            => follow standard JSON format
                . status
                . code
                . message
                . errors
            => Token protections
                . JWT

        - Ticketing with hand-made framework
            => create a list of all reservations
            => add a button `Export into PDF`
            => call the api in `Ticketing Spring Boot`

+ Price verification
    - Verify price calculation