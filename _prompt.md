Enhance this prompt to be more accurate and organized : 

/* ----------------------------------------------------------- */


**Project Context:**  
I have a newly started **Spring Boot MVC** application and need guidance on implementing **authentication functionality**.  

---

### **Requirements:**  

#### **1. Database Schema:**  
I have an `admins` table in my database with the following structure:  
```sql
CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);
```

#### **2. Login View:**  
- Create a **login page** using **Thymeleaf**.  
- Style it using **Bootstrap** and include **icons** for better UI.  

#### **3. Backend Implementation:**  
Implement the necessary files to enable authentication, including:  
- **Model** for `Admin`.  
- **Repository** for database interactions.  
- **Service** to handle authentication logic.  
- **Controller** to manage login requests.  
- **Security Configuration** for authentication and authorization.  

#### **4. Access Control:**  
- Ensure that **non-logged-in users cannot access protected routes** (e.g., `/dashboard`).  
- Implement proper **session handling** and **redirect unauthorized users** to the login page.  


/* --------------------------------------------------------- */

Create a full CRUD functionality for the table `percentage_discount`. Use a single form for insert and update, and add a link to the dashboard header to access the list. Always use bootstrap and icons for the style. 
My package name is mg.itu.[...]  

```
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
```

/* --------------------------------------------------------- */

Given this postgres db scheme: 
```
```

I want to implement the functionality of making a reservation (front office).
The main table will be `reservations` and `reservations_details` but you can use the others tables if it necessary for the functionality.

Here is a brieve description of the functionality: a client needs to log first



/* --------------------------------------------------------- */
why  in my spring boot web mvc app , when i try to log in as a client in the client login form, 'im redirected to the admin dashboard ? What's wring with my authentication functionality ?  


Here is the form in templates/auth/login-client.html:

```
<form class="login-form" th:action="@{/client-login-process}" method="post">
	<div class="form-group">
		<label for="username" class="form-label">Email</label>
		<div class="input-group">
			<span class="input-group-text"><i class="bi bi-envelope"></i></span>
			<input type="email" id="username" name="username" class="form-control" required autofocus>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="form-label">Password</label>
		<div class="input-group">
			<span class="input-group-text"><i class="bi bi-key"></i></span>
			<input type="password" id="password" name="password" class="form-control" required>
			<span class="input-group-text" id="togglePassword">
				<i class="bi bi-eye" id="eyeIcon"></i>
			</span>
		</div>
	</div>
	<div class="d-grid gap-2">
		<button type="submit" class="btn btn-primary">
			<i class="bi bi-box-arrow-in-right"></i> Sign In
		</button>
	</div>
</form>
```

Here is the form in templates/auth/login-admin.html:
```
<form class="login-form" th:action="@{/admin-login}" method="post">
	<div class="form-group">
		<label for="username" class="form-label">Email</label>
		<div class="input-group">
			<span class="input-group-text"><i class="bi bi-envelope"></i></span>
			<input type="text" id="username" name="username" class="form-control" required autofocus>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="form-label">Password</label>
		<div class="input-group">
			<span class="input-group-text"><i class="bi bi-key"></i></span>
			<input type="password" id="password" name="password" class="form-control" required>
			<span class="input-group-text" id="togglePassword">
				<i class="bi bi-eye" id="eyeIcon"></i>
			</span>
		</div>
	</div>
	<div class="d-grid gap-2">
		<button type="submit" class="btn btn-primary">
			<i class="bi bi-box-arrow-in-right"></i> Sign In
		</button>
	</div>
</form>
```


/* ------------------------------------------------- */

**Spring Boot MVC Web App - Reservation Insert Functionality**

Create an `INSERT` functionality for the `reservations` and `reservations_details` tables in a Spring Boot MVC web application using Thymeleaf for the views and Bootstrap for styling. The application should allow clients to make reservations, where each reservation can contain multiple seat details. 

### Database Tables

```sql
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
```

### Functionality Description

1. **Main Table: `reservations`**
   - A client can make a reservation for a flight.
   - The `reservations` table holds the primary reservation details, such as the client ID, flight ID, and the number of tickets (total, children, adults).
   - This table should be filled first.

2. **Details Table: `reservations_details`**
   - The `reservations_details` table contains specific details for each seat in the reservation, such as seat category, passenger name, passport image, price, and promotional/cancellation information.
   - After saving the `reservations` information, this table should be populated.
   - If the total number of seats (`nbr_billet_total`) is 5, then 5 rows must be inserted into the `reservations_details` table.

3. **User Interface:**
   - **Form to Insert Reservation Details:**
     - Create a form for inserting the main reservation details (e.g., client ID, flight ID, number of tickets).
     - After saving the reservation, allow the user to add details for each seat by clicking an "Add Reservation Details" button.
     - The button should allow adding multiple rows based on the total number of seats (`nbr_billet_total`).
   - **Reservation Details Form:**
     - A dynamic form should be presented for each seat, allowing the user to fill out details such as seat category, passenger name, date of birth, passport image, price, and whether the seat is promotional or canceled.
   - **UI/UX Considerations:**
     - Use Bootstrap to style the forms and ensure a responsive design.
     - The forms should be clear and easy to use, with labels, placeholders, and validation messages where appropriate.
     - Consider adding a confirmation page before submission and showing a success/error message after submission.
     - Ensure that the UI follows best practices for user experience, such as providing helpful instructions and validating input fields.

4. **Package Name:**
   - The package name for the application is `mg.itu.[...]`.

### Steps to Implement:

1. **Controller:**
   - Implement a Spring MVC controller to handle the insertion logic for both `reservations` and `reservations_details`.
   - The controller should manage form submissions, validate data, and handle redirections between the reservation form and the details form.

2. **Thymeleaf Templates:**
   - Use Thymeleaf to render the forms and handle dynamic content (such as adding multiple `reservations_details` rows).
   - Ensure that Bootstrap is integrated for a clean and responsive layout.

3. **Database Logic:**
   - Use Spring Data JPA (or JDBC) to manage the persistence of reservation data and ensure the correct number of rows are inserted into `reservations_details` based on the total seat count.

4. **Error Handling:**
   - Implement error handling for invalid inputs (e.g., missing details, invalid date formats, etc.) and display user-friendly error messages in the UI.

### Expected Output:
- A user-friendly interface for making a reservation and managing seat details.
- The reservation is saved first, and once confirmed, the details can be added dynamically based on the number of seats.
- The application should display success or error messages accordingly.