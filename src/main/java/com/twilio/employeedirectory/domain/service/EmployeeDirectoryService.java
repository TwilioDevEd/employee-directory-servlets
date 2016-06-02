package com.twilio.employeedirectory.domain.service;

import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Sevices for requesting employees
 */
public interface EmployeeDirectoryService {

  /**
   * Queries an employee by part of its fullName
   *
   * @param fullName String query of the fullName
   * @param lastQuery Specify the values of the last query
   * @return Implementation of {@link EmployeeMatch} not <code>null</code>
   */
  EmployeeMatch queryEmployee(String fullName, List<NameValuePair> lastQuery);

}
