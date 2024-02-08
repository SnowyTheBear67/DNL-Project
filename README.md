# Instructor Dashboard Full Stack CRUD App

This is a full stack web application that allows instructors to create, read, update, and delete (CRUD) students from a database. The backend is built with Spring Boot, which provides REST APIs for the frontend to consume. The frontend is built with React, which uses React Router for navigation. The database is MySQL, which is accessed by the backend using Spring Data JPA.

## Features

- Instructors can log in
- Instructors can view their students
- Instructors can add students
- Instructors can delete students
- Instrctors can edit students

## Installation

To run this project, you need to have Java, Node.js, and MySQL installed on your machine.

### Backend

- Clone this repository and navigate to the `Instructor-Application` folder
- Open the project in your preferred IDE and import the Gradle dependencies
- Create a MySQL database named `spring_db` and update the `application-dev.properties` file with your database credentials
- Run the `DNL-Project` class to start the Spring Boot server on port 8080

### Frontend

- Navigate to the `instructor-application-frontend` folder and run `npm install` to install the dependencies
- Run `npm start` to start the React development server on port 3000
- Open your browser and go to `http://localhost:3000` to see the app


## Dependencies

The following are the main dependencies used in this project:

- Backend
  - Spring Boot: A framework for building web applications using Java
  - Spring Web: A module for creating RESTful web services using Spring MVC
  - Spring Data JPA: A module for accessing relational databases using JPA and Hibernate
  - MySQL Connector/J: A JDBC driver for connecting to MySQL databases
- Frontend
  - React: A library for building user interfaces using components
  - React Router: A library for managing routing and navigation in React applications
  - Bootstrap: A framework for designing responsive and mobile-first websites

