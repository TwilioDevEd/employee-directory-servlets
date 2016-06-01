package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultiplePartialMatch;
import com.twilio.employeedirectory.domain.query.PerfectMatch;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.sdk.verbs.TwiMLException;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class EmployeeLookupServletTest {

  @Test
  public void shouldReturnTwimlContentForPerfectMatch() throws IOException, TwiMLException {
    EmployeeDirectoryService employeeService = mock(EmployeeDirectoryService.class);
    Employee foundEmployee =
        new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg");
    foundEmployee.setId(1L);
    EmployeeMatch expectedMatch = new PerfectMatch(foundEmployee);

    when(employeeService.queryEmployee("Spider", Collections.emptyList())).thenReturn(expectedMatch);

    EmployeeLookupServlet employeeLookupServlet = new EmployeeLookupServlet(employeeService);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter printWriter = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(printWriter);
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(anyString())).thenReturn("Spider");
    employeeLookupServlet.doGet(request, response);

    verify(printWriter, times(1)).print(expectedMatch.getMessageTwiml());
  }

  @Test
  public void shouldSetACookieWithEmployeesForMultiplePartialMatch() throws IOException
  {
      EmployeeDirectoryService employeeService = mock(EmployeeDirectoryService.class);
      Employee firstEmployee =
              new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
                      "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg");
      firstEmployee.setId(12L);
      Employee secondEmployee = new Employee("Iron Man", "ironMan@heroes.example.com", "+14155559368",
              "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg");
      secondEmployee.setId(21L);
      when(employeeService.queryEmployee("Man", Collections.emptyList()))
              .thenReturn(new MultiplePartialMatch(Arrays.asList(firstEmployee, secondEmployee)));

      EmployeeLookupServlet employeeLookupServlet = new EmployeeLookupServlet(employeeService);
      HttpServletResponse response = mock(HttpServletResponse.class);
      HttpServletRequest request = mock(HttpServletRequest.class);
      when(request.getParameter(anyString())).thenReturn("Man");
      PrintWriter printWriter = mock(PrintWriter.class);
      when(response.getWriter()).thenReturn(printWriter);
      employeeLookupServlet.doGet(request, response);
      verify(response, times(1)).addCookie(refEq(new Cookie(EmployeeLookupServlet.LAST_QUERY_COOKIE_NAME, "1=12&2=21")));
  }

}
