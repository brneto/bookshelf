package ie.ait.ria.bookshelf;

import ie.ait.ria.bookshelf.model.Book;
import ie.ait.ria.bookshelf.model.Seller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@SpringBootApplication
public class BookshelfApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookshelfApplication.class, args);
  }

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return RepositoryRestConfigurer.withConfig(config ->
        config
            .exposeIdsFor(Book.class)
            .exposeIdsFor(Seller.class)
            .getCorsRegistry()
            .addMapping("/api/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("*"));
  }

}
