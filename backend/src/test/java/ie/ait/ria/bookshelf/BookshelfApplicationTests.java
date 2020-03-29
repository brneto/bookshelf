package ie.ait.ria.bookshelf;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import ie.ait.ria.bookshelf.model.Book;
import ie.ait.ria.bookshelf.repository.BookRepository;
import java.net.URI;
import java.util.Collection;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class BookshelfApplicationTests {

  private final URI baseUrl = URI.create("/api/books/");

  // https://rtmccormick.com/2017/07/30/solved-testing-patch-spring-boot-testresttemplate/
  @Autowired
  private TestRestTemplate restTemplate;

  private final Book book = new Book()
      .setTitle("Call of the wild")
      .setAuthor("Jack London")
      .setPublisher("Sterling Children's Books")
      .setPages(56)
      .setLanguage("English")
      .setDescription(
          "Here is the ultimate dog story, one filled with "
              + "emotion, adventure, and excitement. During the Gold Rush, "
              + "Buck is snatched away from his peaceful home and brought to the "
              + "harsh and bitter Yukon to become a sled dog."
      );

  @Test @Order(1)
  void shouldContextLoads(@Autowired BookRepository bookRepository) {
    then(bookRepository).isNotNull();
  }

  @Test @Order(2)
  void shouldHaveNoBookOnStartGet() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(baseUrl)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<CollectionModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<CollectionModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Collection<Book> responseContent = response.getBody().getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent).isEmpty();
  }

  @Test @Order(3)
  void shouldCreateABookWithPost() {
    // given
    RequestEntity<Book> request = RequestEntity
        .post(baseUrl)
        .accept(APPLICATION_JSON)
        .body(book);

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = response.getBody().getContent();

    // then
    then(responseStatus).isEqualTo(CREATED);
    then(responseContent.getTitle()).isEqualTo(book.getTitle());
    then(responseContent.getAuthor()).isEqualTo(book.getAuthor());
    then(responseContent.getPublisher()).isEqualTo(book.getPublisher());
    then(responseContent.getPages()).isEqualTo(book.getPages());
    then(responseContent.getLanguage()).isEqualTo(book.getLanguage());
    then(responseContent.getDescription()).isEqualTo(book.getDescription());
    then(response.getBody().getLinks().hasLink("book")).isTrue();
  }

  @Test @Order(4)
  void shouldHaveOneBookOnGet() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(baseUrl)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<CollectionModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<CollectionModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Collection<Book> responseContent = response.getBody().getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent).isNotEmpty();
  }

  @Test @Order(5)
  void shouldUpdateExistingBookWithPatch() {
    // given
    String bookId = "1";
    String bookTitle = "The call of the wild";
    String requestJson = String.format("{\"id\":%s,\"title\":\"%s\"}", bookId, bookTitle);

    RequestEntity<String> request = RequestEntity
        .patch(baseUrl.resolve(bookId))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .body(requestJson);

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = response.getBody().getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent.getTitle()).isEqualTo(bookTitle);
    then(responseContent.getTitle()).isNotEqualTo(book.getAuthor());
    then(responseContent.getAuthor()).isEqualTo(book.getAuthor());
    then(responseContent.getPublisher()).isEqualTo(book.getPublisher());
    then(responseContent.getPages()).isEqualTo(book.getPages());
    then(responseContent.getLanguage()).isEqualTo(book.getLanguage());
    then(responseContent.getDescription()).isEqualTo(book.getDescription());
  }

  @Test @Order(6)
  void shouldReplaceExistingBookWithPut() {
    // given
    String bookId = "1";
    String bookTitle = "Pollyana";
    String requestJson = String.format("{\"id\":%s,\"title\":\"%s\"}", bookId, bookTitle);
    RequestEntity<String> request = RequestEntity
        .put(baseUrl.resolve(bookId))
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .body(requestJson);

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = response.getBody().getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent.getTitle()).isEqualTo(bookTitle);
    then(responseContent.getAuthor()).isNull();
    then(responseContent.getPublisher()).isNull();
    then(responseContent.getPages()).isNull();
    then(responseContent.getLanguage()).isNull();
    then(responseContent.getDescription()).isNull();
  }

  @Test @Order(7)
  void shouldDeleteExistingBookWithDelete() {
    // given
    String bookId = "1";
    RequestEntity<Void> request = RequestEntity
        .delete(baseUrl.resolve(bookId))
        .build();

    // when
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();

    // then
    then(responseStatus).isEqualTo(NO_CONTENT);
  }

  @Test @Order(8)
  void shouldHaveNoBookOnFinalGet() { shouldHaveNoBookOnStartGet(); }

}
