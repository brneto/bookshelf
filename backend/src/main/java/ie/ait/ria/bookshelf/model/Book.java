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
  private String description;

  public long getId() { return id; }

  public String getTitle() { return title; }

  public String getDescription() { return description; }

  public Book setId(long id) {
    this.id = id;
    return this;
  }

  public Book setTitle(String title) {
    this.title = title;
    return this;
  }

  public Book setDescription(String description) {
    this.description = description;
    return this;
  }

}
