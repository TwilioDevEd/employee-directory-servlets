package com.twilio.employeedirectory.domain.matchers;

/**
 * When there is no match for the queried name
 */
public class NoMatch implements EmployeeMatch {
  public NoMatch() {}

  @Override
  public String getMessage() {
    return "We did not find the employee you're looking for";
  }
}
