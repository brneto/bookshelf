package ie.ait.ria.bookshelf.validator;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

import ie.ait.ria.bookshelf.model.Seller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SellerValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) { return Seller.class.equals(aClass); }

  @Override
  public void validate(Object o, Errors errors) {
    Seller seller = (Seller) o;
    String templateMessage = "%s cannot be empty";
    if (isEmpty(seller.getSellerName())) errors.rejectValue(
        "sellerName", "sellerName.empty", format(templateMessage, "sellerName"));
  }
}
