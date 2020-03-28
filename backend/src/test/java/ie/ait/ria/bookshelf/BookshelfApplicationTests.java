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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import sun.net.www.http.HttpClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureJsonTesters
@TestMethodOrder(OrderAnnotation.class)
class BookshelfApplicationTests {

  private final URI baseUrl = URI.create("/api/books/");

  // https://rtmccormick.com/2017/07/30/solved-testing-patch-spring-boot-testresttemplate/
  @Autowired
  private TestRestTemplate restTemplate;


  @Autowired
  private BasicJsonTester jsonTester;

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
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();
    JsonContent<Object> responseJson = jsonTester.from(response.getBody());

    // then
    then(responseStatus)
        .isEqualTo(OK);
    then(responseJson)
        .extractingJsonPathArrayValue("@._embedded.books")
        .hasSize(0);
  }

  @Test @Order(3)
  void shouldCreateABookWithPost() {
    // given
    Book book = new Book()
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
    RequestEntity<Book> request = RequestEntity
        .post(baseUrl)
        .accept(APPLICATION_JSON)
        .body(book);

    // when
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();
    JsonContent<Object> responseJson = jsonTester.from(response.getBody());

    // then
    then(responseStatus)
        .isEqualTo(CREATED);
    then(responseJson)
        .extractingJsonPathStringValue("@.title")
        .isEqualTo(book.getTitle());
    then(responseJson)
        .extractingJsonPathStringValue("@.author")
        .isEqualTo(book.getAuthor());
    then(responseJson)
        .extractingJsonPathStringValue("@.publisher")
        .isEqualTo(book.getPublisher());
    then(responseJson)
        .extractingJsonPathNumberValue("@.pages")
        .isEqualTo(book.getPages());
    then(responseJson)
        .extractingJsonPathStringValue("@.language")
        .isEqualTo(book.getLanguage());
    then(responseJson)
        .extractingJsonPathStringValue("@.description")
        .isEqualTo(book.getDescription());
    then(responseJson)
        .hasJsonPath("@._links.book");
  }

  @Test @Order(4)
  void shouldHaveOneBookOnGet() {
    // given
    RequestEntity<Void> request = RequestEntity
        .get(baseUrl)
        .accept(APPLICATION_JSON)
        .build();

    // when
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();
    JsonContent<Object> responseJson = jsonTester.from(response.getBody());

    // then
    then(responseStatus)
        .isEqualTo(OK);
    then(responseJson)
        .extractingJsonPathArrayValue("@._embedded.books")
        .hasSize(1);
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
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();
    JsonContent<Object> responseJson = jsonTester.from(response.getBody());

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseJson).extractingJsonPathStringValue("@.title").isEqualTo(bookTitle);
    then(responseJson).extractingJsonPathStringValue("@.author").isNotEmpty();
    then(responseJson).extractingJsonPathStringValue("@.publisher").isNotEmpty();
    then(responseJson).extractingJsonPathNumberValue("@.pages").isNotNull();
    then(responseJson).extractingJsonPathStringValue("@.language").isNotEmpty();
    then(responseJson).extractingJsonPathStringValue("@.description").isNotEmpty();
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
    ResponseEntity<String> response = restTemplate.exchange(request, String.class);
    HttpStatus responseStatus = response.getStatusCode();
    JsonContent<Object> responseJson = jsonTester.from(response.getBody());

    // then
    then(responseStatus).isEqualTo(OK);
    then(responseJson).extractingJsonPathStringValue("@.title").isEqualTo(bookTitle);
    then(responseJson).extractingJsonPathStringValue("@.author").isNull();
    then(responseJson).extractingJsonPathStringValue("@.publisher").isNull();
    then(responseJson).extractingJsonPathNumberValue("@.pages").isNull();
    then(responseJson).extractingJsonPathStringValue("@.language").isNull();
    then(responseJson).extractingJsonPathStringValue("@.description").isNull();
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
  void shouldHaveNoBookOnFinalGet() {
    shouldHaveNoBookOnStartGet();
  }

}
