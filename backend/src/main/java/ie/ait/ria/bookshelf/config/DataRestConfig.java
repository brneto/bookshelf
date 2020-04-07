package ie.ait.ria.bookshelf.config;

import ie.ait.ria.bookshelf.domain.Book;
import ie.ait.ria.bookshelf.domain.Seller;
import ie.ait.ria.bookshelf.validator.BookValidator;
import ie.ait.ria.bookshelf.validator.SellerValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config
        .exposeIdsFor(Book.class)
        .exposeIdsFor(Seller.class)
        .getCorsRegistry()
        .addMapping("/api/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("*");
  }

  @Override
  public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
    v.addValidator("beforeSave", new BookValidator())
     .addValidator("beforeCreate", new BookValidator())
     .addValidator("beforeSave", new SellerValidator())
     .addValidator("beforeCreate", new SellerValidator());
  }

}
