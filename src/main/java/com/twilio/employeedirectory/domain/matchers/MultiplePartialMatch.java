package com.twilio.employeedirectory.domain.matchers;

import com.twilio.employeedirectory.domain.model.Employee;

import java.util.List;
import java.util.stream.IntStream;

/**
 * When the response returns multiple results which are not perfect match of the queried name
 */
public class MultiplePartialMatch implements EmployeeMatch {

    private final List<Employee> foundEmployees;

    public MultiplePartialMatch(List<Employee> foundEmployees) {
        this.foundEmployees = foundEmployees;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        IntStream.range(1, foundEmployees.size()).boxed().forEach(i -> builder.append(String.format("%d %s\n", i, foundEmployees.get(i))));
        return builder.toString();
    }

    public List<Employee> getFoundEmployees() {
        return foundEmployees;
    }
}
