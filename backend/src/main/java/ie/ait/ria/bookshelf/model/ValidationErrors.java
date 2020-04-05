package ie.ait.ria.bookshelf.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors {

  private final List<Violation> violations = new ArrayList<>();

  public List<Violation> getViolations() { return violations; }

  public ValidationErrors addViolation(Violation violation) {
    violations.add(violation);
    return this;
  }

  public static class Violation {
    private final String field;
    private final String message;

    public Violation(String field, String message) {
      this.field = field;
      this.message = message;
    }

    public String getField() { return field; }
    public String getMessage() { return message; }
  }
}
