package ie.ait.ria.bookshelf.validator;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

import ie.ait.ria.bookshelf.domain.Book;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) { return Book.class.equals(aClass); }

  @Override
  public void validate(Object o, Errors errors) {
    Book book = (Book) o;
    String templateMessage = "%s cannot be empty";
    if (isEmpty(book.getTitle())) {
      errors.rejectValue("title", "title.empty", format(templateMessage, "title"));
    }
    if (isEmpty(book.getAuthor())) {
      errors.rejectValue("author", "author.empty", format(templateMessage, "author"));
    }
    if (isEmpty(book.getPublisher())) {
      errors.rejectValue("publisher", "publisher.empty", format(templateMessage, "publisher"));
    }
    if (book.getPages() != null && book.getPages() < 1) {
      errors.rejectValue("pages", "pages.empty", "pages must be greater than 0");
    }
    if (isEmpty(book.getLanguage())) {
      errors.rejectValue("language", "language.empty", format(templateMessage, "language"));
    }
  }
}
