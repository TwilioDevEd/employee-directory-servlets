package com.twilio.employeedirectory.domain.query;

/**
 * When there's no match for the query
 */
public class NoMatch implements EmployeeMatch {

  public NoMatch() { }

  @Override
  public String getMessage() {
    return "We did not find the employee you're looking for";
  }
}
