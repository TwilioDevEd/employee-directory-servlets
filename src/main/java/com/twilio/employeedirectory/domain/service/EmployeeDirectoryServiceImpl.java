package com.twilio.employeedirectory.domain.service;

import com.twilio.employeedirectory.domain.common.Utils;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultipleMatch;
import com.twilio.employeedirectory.domain.query.NoMatch;
import com.twilio.employeedirectory.domain.query.PerfectMatch;
import com.twilio.employeedirectory.domain.repository.EmployeeRepository;
import org.apache.http.NameValuePair;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Handle the Employee Directory Search Logic
 */
@Singleton
public class EmployeeDirectoryServiceImpl implements EmployeeDirectoryService {

  private final EmployeeRepository repository;

  @Inject
  public EmployeeDirectoryServiceImpl(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public EmployeeMatch queryEmployee(String fullName, Optional<List<NameValuePair>> lastQuery) {
    Optional<Employee> requestedEmployee = getRequestedEmployee(fullName, lastQuery);
    List<Employee> matchedEmployees = requestedEmployee.map(Arrays::asList)
            .orElse(repository.findEmployeeByFullName(fullName));
    return createEmployeeMatch(matchedEmployees);
  }

  /**
   * Creates an {@link EmployeeMatch} base on the employees found
   *
   * @param foundEmployees A List of {@link Employee}
   * @return An {@link EmployeeMatch} indicating a particular type of result, not <code>null</code>
   */
  protected EmployeeMatch createEmployeeMatch(List<Employee> foundEmployees) {
    final EmployeeMatch response;
    switch (foundEmployees.size()) {
      case 0:
        response = new NoMatch();
        break;
      case 1:
        response = new PerfectMatch(foundEmployees.get(0));
        break;
      default:
        response = new MultipleMatch(foundEmployees);
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
  protected Optional<Employee> getRequestedEmployee(String optionIndex,
      Optional<List<NameValuePair>> availableOptions) {
    if (availableOptions.isPresent()) {
      Optional<NameValuePair> choosenOption =
          availableOptions.get().stream().filter(pair -> pair.getName().equals(optionIndex))
              .findFirst();
      if (choosenOption.isPresent()) {
        Optional<Long> selectedId = Utils.getOptionalLong(choosenOption.get().getValue());
        if (selectedId.isPresent()) {
          return repository.findEmployeeById(selectedId.get());
        }
      }
    }
    return Optional.empty();
  }
}
