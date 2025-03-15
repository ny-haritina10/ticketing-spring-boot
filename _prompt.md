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