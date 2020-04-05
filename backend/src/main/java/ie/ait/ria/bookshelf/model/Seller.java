package ie.ait.ria.bookshelf.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Seller {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private long id;

  @NotBlank(message = "Seller Name cannot be empty")
  private String sellerName;
  private String location;
  private Integer rating;

  @OneToMany(mappedBy = "seller")
  private List<Book> books = new ArrayList<>();

  public long getId() { return id; }

  public String getSellerName() { return sellerName; }

  public String getLocation() { return location; }

  public Integer getRating() { return rating; }

  public List<Book> getBooks() { return Collections.unmodifiableList(books); }

  public Seller setId(long id) {
    this.id = id;
    return this;
  }

  public Seller setSellerName(String sellerName) {
    this.sellerName = sellerName;
    return this;
  }

  public Seller setLocation(String location) {
    this.location = location;
    return this;
  }

  public Seller setRating(Integer rating) {
    this.rating = rating;
    return this;
  }
}
