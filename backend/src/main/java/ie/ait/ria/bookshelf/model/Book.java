package ie.ait.ria.bookshelf.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

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

  private String description;

  @ManyToOne
  private Seller seller;

  public long getId() { return id; }

  public String getTitle() { return title; }

  public String getAuthor() { return author; }

  public String getPublisher() { return publisher; }

  public Integer getPages() { return pages; }

  public String getLanguage() { return language; }

  public String getDescription() { return description; }

  public Seller getSeller() { return seller; }

  public Book setId(long id) {
    this.id = id;
    return this;
  }

  public Book setTitle(String title) {
    this.title = title;
    return this;
  }

  public Book setAuthor(String author) {
    this.author = author;
    return this;
  }

  public Book setPublisher(String publisher) {
    this.publisher = publisher;
    return this;
  }

  public Book setPages(Integer pages) {
    this.pages = pages;
    return this;
  }

  public Book setLanguage(String language) {
    this.language = language;
    return this;
  }

  public Book setDescription(String description) {
    this.description = description;
    return this;
  }

  public Book setSeller(Seller seller) {
    this.seller = seller;
    return this;
  }
}
