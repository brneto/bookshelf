package ie.ait.ria.bookshelf.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book implements Serializable {

  @Id
  @GeneratedValue
  private long id;
  private String title;
  private String author;
  private String publisher;
  private Integer pages;
  private String language;
  private String description;

  public long getId() { return id; }

  public String getTitle() { return title; }

  public String getAuthor() { return author; }

  public String getPublisher() { return publisher; }

  public Integer getPages() { return pages; }

  public String getLanguage() { return language; }

  public String getDescription() { return description; }

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

}
