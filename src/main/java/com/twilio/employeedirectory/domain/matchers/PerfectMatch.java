package com.twilio.employeedirectory.domain.matchers;

import com.twilio.employeedirectory.domain.model.Employee;

/**
 * When it returns an {@link com.twilio.employeedirectory.domain.model.Employee} which name is
 * exactly the one queried.
 */
public class PerfectMatch implements EmployeeMatch {

  private final Employee foundEmployee;

  public PerfectMatch(Employee foundEmployee) {
    this.foundEmployee = foundEmployee;
  }

  @Override
  public String getMessage() {
    return String.format("%s\n%s\n%s", foundEmployee.getFullName(), foundEmployee.getPhoneNumber(),
        foundEmployee.getEmail());

  }

  public Employee getFoundEmployee() {
    return foundEmployee;
  }

  @Override
  public boolean isSingleEmployeeFound() {
    return true;
  }
}
