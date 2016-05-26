package com.twilio.employeedirectory.domain.service;

import com.twilio.employeedirectory.domain.matchers.*;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.repository.EmployeeRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handle service for Employees
 */
@Singleton
public class EmployeeDirectoryServiceImpl implements EmployeeDirectoryService {

  private static final Logger LOG = Logger.getLogger(EmployeeDirectoryServiceImpl.class.getName());

  private static final String JSON_PATH = "seed-data.json";

  private EmployeeRepository repository;

  @Inject
  public EmployeeDirectoryServiceImpl(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public EmployeeMatch queryEmployee(String fullName) {
    List<Employee> matchedEmployees = repository.findEmployeeByFullName(fullName);
    final EmployeeMatch response;
    switch (matchedEmployees.size()) {
      case 0:
        response = new NoMatch();
        break;
      case 1:
        response = new PerfectMatch(matchedEmployees.get(0));
        break;
      default:
        response = new MultiplePartialMatch(matchedEmployees);
    }
    return response;
  }
}
