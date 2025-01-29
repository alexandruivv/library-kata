# **Library Kata Application**

Welcome to the **Library Kata** application! This project includes both a **backend (Spring Boot)** and a **frontend (Angular)**, designed to manage a library's book catalog and borrowing system.

---

### **Features**

1. Add a new book to the catalog (**Admin** only)
2. Borrow a book (**User**)
3. Return a book (**User**)
4. View a list of borrowed books (**User**)

---

### **Backend Setup Instructions**

#### **Requirements**
- Java 17+
- Maven 3+

#### **Steps to Run the Backend**
1. Navigate to the project directory containing the backend code.
2. Start the Spring Boot application by running the `LibraryKataApplication` class:
   mvn spring-boot:run

3. The backend will start at: http://localhost:8080

---

### **Database Setup**

The application uses an **H2 in-memory database**. Upon startup, the backend automatically loads default data from the `data.sql` file located in the `resources` directory.

You can access the H2 Console to explore the database:
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

---

### **Frontend Setup Instructions**

#### **Requirements**
- Node.js 18+
- npm (Node Package Manager)
- Angular CLI (npm install -g @angular/cli)

#### **Steps to Run the Frontend**
1. Navigate to the frontend directory:
   cd library-kata-fe

2. Install dependencies:
   npm install

3. Start the frontend application:
   ng serve

4. Access the application at: http://localhost:4200

---

### **Login Credentials**

| Username | Password | Role       |
|----------|----------|------------|
| ADMIN    | ADMIN    | Admin User |
| user1    | user1    | Normal User |
| user2    | user2    | Normal User |

---

### **Application Roles and Permissions**

- **Admin User**:
    - Add new books to the catalog
    - View the list of all books

- **Normal User**:
    - Borrow books from the catalog
    - Return borrowed books
    - View the list of books they have borrowed

---

### **Troubleshooting**

1. **Backend Fails to Start**
    - Ensure Java and Maven are installed and properly configured.
    - Check that port 8080 is not already in use.

2. **Frontend Issues**
    - Ensure Node.js and Angular CLI are installed.
    - Run npm install to ensure all dependencies are installed.
    - Make sure the backend is running on port 8080.

---

### **Example Usage**

1. **Admin Login**:
    - Navigate to http://localhost:4200 and log in as `ADMIN/ADMIN`.
    - Add a new book by filling out the book form.

2. **User Login**:
    - Log in as `user1/user1`.
    - View the catalog and borrow a book.
    - View your borrowed books list or return a borrowed book.

3. **User Login**:
    - Log in as `user2/user2`.
    - View the catalog and borrow a book.
    - View your borrowed books list or return a borrowed book.

---

### **Thank You for Using Library Kata!**
