package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.common.Twilio;
import com.twilio.employeedirectory.domain.common.Utils;
import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultiplePartialMatch;
import com.twilio.employeedirectory.domain.query.NoMatch;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.sdk.verbs.TwiMLException;
import org.apache.http.NameValuePair;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Singleton
public class EmployeeLookupServlet extends HttpServlet {

  public static final String LAST_QUERY_COOKIE_NAME = "last-query";

  private EmployeeDirectoryService employeeDirectoryService;

  @Inject
  public EmployeeLookupServlet(EmployeeDirectoryService employeeDirectoryService) {
    this.employeeDirectoryService = employeeDirectoryService;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Optional<String> fullNameQuery = Optional.ofNullable(request.getParameter(Twilio.QUERY_PARAM));
    final EmployeeMatch matchResponse =
        fullNameQuery.map(
            queryValue -> {
              Optional<List<NameValuePair>> optionsOfEmployees =
                  Utils.getCookieAndDispose(request, response, LAST_QUERY_COOKIE_NAME);
              return employeeDirectoryService.queryEmployee(queryValue, optionsOfEmployees);
            }).orElse(new NoMatch());
    request.setAttribute("employeeMatch", matchResponse);
    try {
      // Only MultiplePartialMatch caches its response in a cookie
      if (matchResponse instanceof MultiplePartialMatch) {
        Cookie lastQueryCookie =
            new Cookie(LAST_QUERY_COOKIE_NAME,
                ((MultiplePartialMatch) matchResponse).getLastQueryOptions());
        response.addCookie(lastQueryCookie);
      }
      response.setContentType("text/xml");
      response.getWriter().print(matchResponse.getMessageTwiml());
    } catch (TwiMLException e) {
      throw new RuntimeException(e);
    }
  }
}
