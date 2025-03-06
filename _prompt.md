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