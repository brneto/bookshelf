package ie.ait.ria.bookshelf;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.ait.ria.bookshelf.model.Book;
import ie.ait.ria.bookshelf.repository.BookRepository;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
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

  private final URI baseUri = URI.create("/api/books/");
  private final HashMap<String, Object> bookUpdater = new HashMap<>();
  private final Long bookIdInTest = 1L;
  private final URI bookUriInTest = baseUri.resolve(bookIdInTest.toString());

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
  void shouldHaveNoBookWithGetAll() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(baseUri)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<CollectionModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<CollectionModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Collection<Book> responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent).isEmpty();
  }

  @Test @Order(3)
  void shouldCreateABookWithPost() {
    // given
    RequestEntity<Book> request = RequestEntity
        .post(baseUri)
        .accept(APPLICATION_JSON)
        .body(book);

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(CREATED);
    assert responseContent != null;
    then(responseContent.getId()).isEqualTo(1);
    then(responseContent.getTitle()).isEqualTo(book.getTitle());
    then(responseContent.getAuthor()).isEqualTo(book.getAuthor());
    then(responseContent.getPublisher()).isEqualTo(book.getPublisher());
    then(responseContent.getPages()).isEqualTo(book.getPages());
    then(responseContent.getLanguage()).isEqualTo(book.getLanguage());
    then(responseContent.getDescription()).isEqualTo(book.getDescription());
    then(response.getBody().getLinks().hasLink("book")).isTrue();
  }

  @Test @Order(4)
  void shouldHaveOneBookWithGetAll() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(baseUri)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<CollectionModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<CollectionModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Collection<Book> responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseContent).isNotEmpty();
    then(responseContent.size()).isPositive();
  }

  @Test @Order(5)
  void shouldReturnOneBookWithGet() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(bookUriInTest)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    assert responseContent != null;
    then(responseContent.getId()).isEqualTo(1);
    then(responseContent.getTitle()).isEqualTo(book.getTitle());
    then(responseContent.getAuthor()).isEqualTo(book.getAuthor());
    then(responseContent.getPublisher()).isEqualTo(book.getPublisher());
    then(responseContent.getPages()).isEqualTo(book.getPages());
    then(responseContent.getLanguage()).isEqualTo(book.getLanguage());
    then(responseContent.getDescription()).isEqualTo(book.getDescription());
    then(response.getBody().getLinks().hasLink("book")).isTrue();
  }

  @Test @Order(6)
  void shouldUpdateExistingBookWithPatch(@Autowired ObjectMapper objectMapper)
      throws JsonProcessingException {
    // given
    String newTitle = "The call of the wild";
    bookUpdater.put("title", newTitle);

    RequestEntity<String> request = RequestEntity
        .patch(bookUriInTest)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(bookUpdater));

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    assert responseContent != null;
    then(responseContent.getId()).isEqualTo(1);
    then(responseContent.getTitle()).isEqualTo(newTitle);
    then(responseContent.getTitle()).isNotEqualTo(book.getAuthor());
    then(responseContent.getAuthor()).isEqualTo(book.getAuthor());
    then(responseContent.getPublisher()).isEqualTo(book.getPublisher());
    then(responseContent.getPages()).isEqualTo(book.getPages());
    then(responseContent.getLanguage()).isEqualTo(book.getLanguage());
    then(responseContent.getDescription()).isEqualTo(book.getDescription());
  }

  @Test @Order(7)
  void shouldReplaceExistingBookWithPut(@Autowired ObjectMapper objectMapper)
      throws JsonProcessingException {
    // given
    String newTitle = "Pollyana";
    bookUpdater.put("title", newTitle);

    RequestEntity<String> request = RequestEntity
        .put(bookUriInTest)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(bookUpdater));

    // when
    ResponseEntity<EntityModel<Book>> response =
        restTemplate.exchange(request, new ParameterizedTypeReference<EntityModel<Book>>() {});
    HttpStatus responseStatus = response.getStatusCode();
    Book responseContent = requireNonNull(response.getBody()).getContent();

    // then
    then(responseStatus).isEqualTo(OK);
    assert responseContent != null;
    then(responseContent.getId()).isEqualTo(1);
    then(responseContent.getTitle()).isEqualTo(newTitle);
    then(responseContent.getAuthor()).isNull();
    then(responseContent.getPublisher()).isNull();
    then(responseContent.getPages()).isNull();
    then(responseContent.getLanguage()).isNull();
    then(responseContent.getDescription()).isNull();
  }

  @Test @Order(8)
  void shouldDeleteExistingBookWithDelete() {
    // given
    RequestEntity<Void> request = RequestEntity
        .delete(bookUriInTest)
        .build();

    // when
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();

    // then
    then(responseStatus).isEqualTo(NO_CONTENT);
  }

  @Test @Order(9)
  void shouldHaveNoBookWithLastGetAll() { shouldHaveNoBookWithGetAll(); }

}
