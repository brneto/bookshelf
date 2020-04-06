# Book Shelf App Project
This is a full stack CRUD application as a required assignment for the course of Rich Internet Application of the [AIT](ait.ie) Msc. Degree in Software Engineering.

## Table of Contents
- [Getting Started](#getting-started)
  - [Backend](#running-backend)
  - [Frontend](#running-frontend)
- [Documentation](#documentation)
  - [Introduction](#introduction)
  - [Backend Design](#backend-design)
    - [Entities Classes](#entities-classes)
    - [Swagger Documentation](#swagger-documentation)
    - [Test Cases](#test-cases)
    - [REST Validation](#rest-validation)
    - [HAL Explorer](#hal-explorer)    
  - [Frontend Design](#frontend-design)
    - [Main Patterns](#main-patterns)
    - [Endpoints](#endpoints)

## Getting Started
To use this application is recommended start the backend before the frontend, otherwise the frontend can not work properly or present unexpected behaviour.

### Running Backend
Given you have already installed the [Apache Maven](https://maven.apache.org/install.html), before start running the backend application first it's needed to download the project dependencies. This is done executing the command below inside the `<project-folder>/backend/` directory:
```
$ mvn test-compile
```
Once the command finishes press `Enter` and then run the following inside the same directory:
```
$ mvn spring-boot:run
```
You can confirm you the backend is up and running accessing the [HAL explorer](https://github.com/toedter/hal-explorer) on the link http://localhost:8080/api.

### Running Frontend
Given you have already installed the [Yarn - Package Manager](https://classic.yarnpkg.com/en/docs/install/), on the frontend side the dependencies is downloaded by running the following command inside the `<project-folder>/frontend/` directory:
```
$ yarn
```
After the command ends running execute statement below in the same directory:
```
$ yarn start
```
From your Browser now you can access the address http://localhost:3000 to start using this Web Application.

## Documentation
### Introduction
The application created to this project is just a Book Shelf manager.

You can add a new Book, edit an existing one or delete it if you want to. Additionaly to that, you can sort the list of Book *By Author or Title* or you can filter in the list by the same fields.

This app requires all fields in registration and editition are filled properly so the frontend side do a validation of them all and only allows the submittion after all fields has been validated and approved.

For this application I did put very effort on styling so the table page, icons and buttons are very basic.

![Main page screenshot][main-page]

![Create or Edit page screenshot][edit-page]

### Backend Design
The section will describe in more depth the design, endpoint and documentation adopted on this part of the app.

#### Entities Classes
Here I created basically two Entities. One and the main one *Book Entity Class* and another *Seller Entity Class*. The relationship between then is represented by the image below:

![Entities class diagram][entities-diagram]

For the Book entity the fields `title`, `author`, `publisher`, `pages` and `language` should not be empty. Therefore to pages it should not be 0 or less. I enforced this behaviour from the backend side with the snippet code:
```java
@NotBlank(message = "Title cannot be empty")
private String title;

@NotBlank(message = "Author cannot be empty")
private String author;

@NotBlank(message = "Publisher cannot be empty")
private String publisher;

@Positive
private Integer pages;

@NotBlank(message = "Language cannot be empty")
private String language;
```

For the Seller entity the only required field is `seller name` which is enforced by the code:
```java
@NotBlank(message = "Seller Name cannot be empty")
private String sellerName;
```

#### Swagger Documentation
To expose the endpoint documentation I used [Swagger](https://springfox.github.io/springfox). To do that a created a *Spring Java Config* specific to tweak some custom changes on *Swagger Docket*. Below the snippet code from that class:
```java
Configuration
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {
  @Bean
  public Docket bookDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .tags(
            new Tag("Book Entity", "Repository for Book entities"),
            new Tag("Seller Entity", "Repository for Seller entities"))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(regex("/api/(books|sellers).*"))
        .build();
  }
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Book Shelf API")
        .description("Rich Internet Application subject project.")
        .version("0.0.1")
        .build();
  }
}
```

#### Test Cases
All the test for the backend was implemented using [Spring Boot Test](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing). To setup the test class I've just created a POJO class inside the Maven test folder and added at class level the following annotations:
```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureJsonTesters
@TestMethodOrder(OrderAnnotation.class)
```
Because I wanted the test run in a certain order I have also add on method level of each test the annotations:
```java
@Test @Order(<order-number>)
```

Below I present the list of test cases implemented by the class `BookshelfApplicationTests`:
1. shouldContextLoads
   - The simplest one, just test the load of the spring boot itself.
2. shouldHaveNoBookWithGetAll
   - Test the REST GET all call when no books has been created yet. It does that checking if the json object create return a list of empty items.
3. shouldFailCreateABookWithPost
   - Test the REST POST call with Book payload with some fields of the json object hasn't been set. It tests for a fail response with HTTP Error Code `400 Bad Request`.
4. shouldCreateABookWithPost
   - Test a successful REST POST call with a Book as the payload. It verifies if the response json object match with the request payload fields.
5. shouldHaveOneBookWithGetAll
   - Test the REST GET all call. It checks if the response Header is `200 OK` and if the returned json list is not empty and his size bigger than 0.
6. shouldReturnOneBookWithGet
   - Test the REST GET one call. It checks if the response Header is `200 OK` and if the returned json object matchs every field with the Book object previously create the POST call.
7. shouldUpdateExistingBookWithPatch
   - Test the REST PATCH call. Althought it send a payload with just title parameter been set, it checks if the response Header is `200 OK` and if all the field but the title still matching the previously createdBook object.
8. shouldFailReplaceExistingBookWithPut
   - Test the REST PUT call to send as payload with just title parameter set. It checks if the response Header is `400 Bad Request` and expect for an Errors object of size 3 and matching json String with the fields missing.
9.  shouldReplaceExistingBookWithPut
    - Test a successful REST PUT call with only the required fields. It checks if the response Header is `200 OK` and test if all fields but description match the PUT payload. The description is matched for null as a prove that in the case of PUT the object is replace by a need one which doesn't happen in the PATCH call test.
10. shouldDeleteExistingBookWithDelete
    - Test the REST DELETE one call of the object created. It checks if the response Header is `204 No Content`.
11. shouldHaveNoBookWithLastGetAll
    - Test the REST GET all call. Just reproduce the same validation as the test `shouldHaveNoBookWithGetAll`. The goal here is just verify if the previous DELETE call has actually deleted the Book object created.

#### REST Validation
For this project I have used the [Spring Boot Data Rest](https://spring.io/projects/spring-data-rest) so in this case to validate the json payload and response a Errors object presenting each required field missed the configuration for that is a little different from when using just [Spring Boot MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc).

Before anything I created to special POJO classes which implements the Validator interface. Those class were `BookValidator` and `SellerValidator`. Inside these classes I test each field of the objects to see if the received object is following que requirements and when this happens I fill a special object called `Errors` with this information.

Once that is done the last step is just link this validator to each Data Rest event I to validate before. This done by a DataRestConfig class I have created which implements the Interface `RepositoryRestConfigurer` and *Override* the method `configureValidatingRepositoryEventListener`. Below the snippet code of this configuration:
```java
@Override
public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
  v.addValidator("beforeSave", new BookValidator()),
   .addValidator("beforeCreate", new BookValidator()),
   .addValidator("beforeSave", new SellerValidator()),
   .addValidator("beforeCreate", new SellerValidator());
}
```

#### HAL Explorer
An additional tool (not request by the project) I added to my project was [HAL Explorer](https://github.com/toedter/hal-explorer). This piece of sofware allows us to browse and explore HAL and HAL-FORMS based RESTful Hypermedia APIs. With this tool is much easier to test my api, create some requests, check the responses and so on. It's like a onboaded POSTMAN by specificaly to the API of my own application.

If you decide to try it just open from your browser the link http://localhost:8080/api once you backend application is up and running. If you don't know it give yourself a try. It's very interesting!

<!-- image references -->
[main-page]: ./images/main-page.png "Main page screenshot"
[edit-page]: ./images/create-edit-page.png "Create or Edit page screenshot"
[entities-diagram]: ./images/entities-diagram.png "Entities class diagram"