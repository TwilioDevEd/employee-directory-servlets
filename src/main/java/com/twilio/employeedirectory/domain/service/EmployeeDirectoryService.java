package com.twilio.employeedirectory.domain.service;

import com.twilio.employeedirectory.domain.matchers.EmployeeMatch;

/**
 * Sevices for requesting employees
 */
public interface EmployeeDirectoryService {

  /**
   * Queries an employee by part of its fullName
   * 
   * @param fullName String query of the fullName
   * @return Implementation of {@link EmployeeMatch} not <code>null</code>
   */
  public EmployeeMatch queryEmployee(String fullName);

}
