Ride Ease: Your Journey, Simplified 🚗
Welcome to Ride Ease, a two-sided car rental management system that securely connects car rental owners and customers on a user-friendly digital platform.

🌟 Core Features & Solutions
The Ride Ease platform addresses key challenges in car rental management with several core functionalities:

Secure Platform: It ensures a secure environment for all user interactions through modern authentication and authorization.

Vehicle Management: The system gives car rental owners full CRUD (Create, Read, Update, Delete) capabilities to manage their car listings.

Seamless Booking: It provides a complete and intuitive workflow for booking and simulating payments.

Dual User Roles: The platform is designed with distinct functionalities for both customers and admins.

💻 Technology Stack
Ride Ease is built using a robust set of technologies to ensure a secure and efficient experience.

Backend
Core Development: Java and Spring Boot

Authentication: Spring Security for robust security

Database Interaction: Spring Data JPA and Hibernate

Data Storage: MySQL

Dependency Management: Maven

Frontend
Dynamic Rendering: Thymeleaf for dynamic HTML

Structure & Styling: HTML5 & CSS3

Responsive UI: Bootstrap 5

🛠️ System Architecture
The system follows the Model-View-Controller (MVC) architecture.

Model: Manages the data structure, with Java entities mapping to the database tables.

View: Represents the user interface, consisting of Thymeleaf HTML files.

Controller: Handles user requests, processes the business logic, and returns the appropriate views.

🗄️ Database Schema
The database schema is designed to manage all necessary information for the car rental process:

customer: Stores customer registration details, including hashed passwords for security.

owner: Stores registration details for vehicle owners.

car: Contains all the details of vehicles listed by owners.

booking: Links customers to specific cars for defined periods.

payment: Stores transaction details related to bookings.

✨ Key Functionalities by User Role
The system provides specific functionalities tailored to each user role.

Authentication & Security 🔒
Dual-role registration for both customers and admins.

Secure login with BCrypt encryption.

Role-based access control to ensure users only access what they need.

Owner Functionality 💼
A dashboard for managing vehicles with full CRUD functionality.

The ability to view bookings and payment information.

Customer Functionality 🙋‍♀️
A dashboard and a car search feature to find available vehicles.

A complete booking system with a simulated payment workflow.

Displays booking details for easy reference.