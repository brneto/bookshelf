package ie.ait.ria.bookshelf.repository;

import ie.ait.ria.bookshelf.domain.Seller;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

  List<Seller> findByRatingGreaterThanEqual(Integer rating);

}
