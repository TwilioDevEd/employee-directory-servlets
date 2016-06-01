package com.twilio.employeedirectory.domain.query;

import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;


public class MultipleMatchTest {

    @Test
    public void testGetLastQueryId() throws Exception {
        EmployeeDirectoryService employeeService = mock(EmployeeDirectoryService.class);
        Employee firstEmployee =
                new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
                        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg");
        firstEmployee.setId(12L);
        Employee secondEmployee = new Employee("Iron Man", "ironMan@heroes.example.com", "+14155559368",
                "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg");
        secondEmployee.setId(21L);
        MultipleMatch match = new MultipleMatch(Arrays.asList(firstEmployee,secondEmployee));
        Assert.assertEquals("The query str didnt was the expected", "1=12&2=21", match.getEmployeeSuggestions());
    }
}