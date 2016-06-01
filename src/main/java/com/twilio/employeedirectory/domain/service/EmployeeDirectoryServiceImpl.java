package com.twilio.employeedirectory.domain.service;

import com.twilio.employeedirectory.domain.common.Utils;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultiplePartialMatch;
import com.twilio.employeedirectory.domain.query.NoMatch;
import com.twilio.employeedirectory.domain.query.PerfectMatch;
import com.twilio.employeedirectory.domain.repository.EmployeeRepository;
import org.apache.http.NameValuePair;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Handle service for Employees
 */
@Singleton
public class EmployeeDirectoryServiceImpl implements EmployeeDirectoryService {

  private final EmployeeRepository repository;

  @Inject
  public EmployeeDirectoryServiceImpl(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public EmployeeMatch queryEmployee(String fullName, List<NameValuePair> lastQuery) {
    Optional<Employee> requestedEmployee = getRequestedEmployee(fullName, lastQuery);
    List<Employee> matchedEmployees = requestedEmployee.map(employee -> {
      List employees = new LinkedList<>();
      employees.add(requestedEmployee.get());
      return employees;
    }).orElse(repository.findEmployeeByFullName(fullName));
    return convertToEmployeeMatch(matchedEmployees);
  }

  /**
   * Converts found Employees to an implementation of {@link EmployeeMatch}
   *
   * @param foundEmployees A List of {@link Employee}
   * @return An {@link EmployeeMatch} indicating a particular type of result, not <code>null</code>
   */
  protected EmployeeMatch convertToEmployeeMatch(List<Employee> foundEmployees) {
    final EmployeeMatch response;
    switch (foundEmployees.size()) {
      case 0:
        response = new NoMatch();
        break;
      case 1:
        response = new PerfectMatch(foundEmployees.get(0));
        break;
      default:
        response = new MultiplePartialMatch(foundEmployees);
    }
    return response;
  }

  /**
   * Detects if an {@link Employee} was requested and returns it
   *
   * @param optionIndex The option index (1,2..) in an input {@link String}
   * @param availableOptions A possible list of {@link NameValuePair} with the <code>id</code> of
   *        the employee in every {@link NameValuePair#getValue()}
   * @return an {@link Optional} with the {@link Employee} correspondant to the
   *         <code>optionIndex</code>
   */
  private Optional<Employee> getRequestedEmployee(String optionIndex, List<NameValuePair> availableOptions) {
    return availableOptions.stream().filter(pair -> pair.getName().equals(optionIndex))
            .findFirst().flatMap(this::getEmployeeFromOption);
  }

  /**
   * Given some option returns the correspondant {@link Employee}
   *
   * @param choosenOption an {@link Optional} of {@link NameValuePair} linking the chosen option
   *        index with the wanted {@link Employee#id}
   * @return an {@code Optional with the {@link Employee} found, not <code>null</code>
  */

  private Optional<Employee> getEmployeeFromOption(NameValuePair choosenOption) {
    return Utils.getOptionalLong(choosenOption.getValue()).map(id -> repository.findEmployeeById(id)).get();
  }

}
