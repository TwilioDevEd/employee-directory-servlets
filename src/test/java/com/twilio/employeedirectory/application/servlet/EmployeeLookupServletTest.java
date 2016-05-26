package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.repository.EmployeeRepository;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeLookupServletTest {

    @Test
    public  void shouldInformTheUserThatEmployeeWasNotFound() throws ServletException, IOException {
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findEmployeeByFullName(anyString())).thenReturn(new ArrayList<>());

        EmployeeLookupServlet indexServlet = new EmployeeLookupServlet(employeeRepository);

        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);

        indexServlet.doGet(request, response);

        verify(printWriter, times(1)).print("<Response><Message>No Employee Found</Message></Response>");
    }

    @Test
    public void shouldInformEmployeeNameWhenOneEmployeeIsFound() throws IOException {
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        ArrayList<Employee> employees = new ArrayList<Employee>(){{
            add(new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
                    "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"));
        }};

        when(employeeRepository.findEmployeeByFullName(anyString())).thenReturn(employees);

        EmployeeLookupServlet indexServlet = new EmployeeLookupServlet(employeeRepository);

        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(printWriter);

        HttpServletRequest request = mock(HttpServletRequest.class);

        indexServlet.doGet(request, response);

        verify(printWriter, times(1)).print("<Response><Message>Spider-man found!</Message></Response>");
    }

}