# Book Shelf App Project
This is a full stack CRUD application as a required assignment for the course of Rich Internet Application of the Msc. Degree in Software Engineering of [AIT](ait.ie).

## Table of Contents
- [Getting Started](#getting-started)
  - [Backend](#launch-backend)
  - [Frontend](#launch-frontend)
- [Documentation](#documentation)
  - [Introduction](#introduction)
  - [Backend Design](#backend-design)
    - [Entities Classes](#entities-classes)
    - [Swagger Documentation](#swagger-documentation)
    - [HAL Explorer](#hal-explorer)
    - [Test Cases](#test-cases)
  - [Frontend Design](#frontend-design)
    - [Main Patterns](#main-patterns)
    - [Endpoints](#endpoints)

## Getting Started
To use this application is recommended start the backend before the frontend, otherwise the frontend can not work properly or present unexpected behaviour.

### Launch Backend
Before start running the backend application first it's needed to download the project dependencies. This is done executing the command below inside the `<project-folder>/backend/` directory:
```
$ mvn test-compile
```
Once the command finishes press `Enter` and then run the following inside the same directory:
```
$ mvn spring-boot:run
```
You can confirm you the backend is up and running accessing the endpoint [HAL explorer](http://localhost:8080/api) or the link http://localhost:8080/api/books.

### Launch Frontend
On the frontend side the dependencies is downloaded by running the following command inside the `<project-folder>/frontend/` directory:
```
$ yarn
```
After the command ends running execute statement below in the same directory:
```
$ yarn start
```
From your Browser now you can access the address http://localhost:3000 to start using this Web Application.

## Documentation

### Rich Internet Application Msc. Degree Subject Project
---
#### 40% of Overall Mark

The purpose of this assignment is to demonstrate your ability to apply the learnings from the module to build a client / server single page Rich Internet Application. This application is expected to provide the following features:

+ Expose the Database tables as Entities within a Java based server application using Spring Data JPA (MySQL Database schema defined in Databases Module)

  + Support Create, Retrieve, Update and Delete (CRUD) operations on the Database via a JpaRepository.

  + Support Search functionality of the Entities using at **least two** derived queries.

  +	Support Sorting and Pagination at the Repository level.

+ Expose the Entities to a Web client via REST resources built with Spring Web MVC.

   + Support full CRUD operations for the resources.

   + Support Validation on Create and Update operations.

   + Demonstrate proper use of HttpStatus Codes and Location headers.

   + Demonstrate proper use of Exceptions to control returned HTTP status codes.

   + All REST endpoints should be documented using Swagger 2 including correct use of the @ApiModel and @ApiModelProperty annotations. You should also override the default API_INFO to display correct details in Swagger.

   + Suite of POSTMAN tests demonstrating each of the REST Endpoints.

+ Create a React.js, Single Page Application to provide CRUD operations against a subset of the Tables (Stock management tables).

  + This should be modelled as a Master/Details type view, whereby the product summary is displayed in a list/table view.  Selecting an item in the table will show the full details in a separate pane.

  + Support the Create/Update/Delete operations either through buttons in the detailed pane, or through icons in the table or a mix of both.

  + Allow Sorting and Filtering (Search by at **least two** properties) of the list/table view.

