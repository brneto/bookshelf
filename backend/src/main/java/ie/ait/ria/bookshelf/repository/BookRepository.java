package ie.ait.ria.bookshelf.repository;

import ie.ait.ria.bookshelf.domain.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByPagesGreaterThanEqual(Integer pages);

  List<Book> findByPublisher(String publisher);

}
