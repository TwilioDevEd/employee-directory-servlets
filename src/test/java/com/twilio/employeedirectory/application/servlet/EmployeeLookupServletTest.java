package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.matchers.EmployeeMatch;
import com.twilio.employeedirectory.domain.matchers.PerfectMatch;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.sdk.verbs.TwiMLException;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeLookupServletTest {

  @Test
  public void shouldInformEmployeeNameWhenOneEmployeeIsFound() throws IOException, TwiMLException {
    EmployeeDirectoryService employeeService = mock(EmployeeDirectoryService.class);
    Employee foundEmployee =
        new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg");
    EmployeeMatch expectedMatch = new PerfectMatch(foundEmployee);

    when(employeeService.queryEmployee("Spider")).thenReturn(expectedMatch);

    EmployeeLookupServlet employeeLookupServlet = new EmployeeLookupServlet(employeeService);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter printWriter = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(printWriter);
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(anyString())).thenReturn("Spider");
    employeeLookupServlet.doGet(request, response);

    verify(printWriter, times(1)).print(expectedMatch.getMessageTwiml());
  }

}
