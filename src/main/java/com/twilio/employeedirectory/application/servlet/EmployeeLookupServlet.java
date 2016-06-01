package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.common.Twilio;
import com.twilio.employeedirectory.domain.common.Utils;
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
            queryValue -> getEmployeeMatch(request, response, queryValue)).orElse(new NoMatch());
    request.setAttribute("employeeMatch", matchResponse);
    returnMatch(response, matchResponse);
  }

  private void returnMatch(HttpServletResponse response, EmployeeMatch matchResponse) throws IOException {
    try {
      // Only MultiplePartialMatch caches its response in a cookie
      if (matchResponse instanceof MultiplePartialMatch) {
        cache(response, (MultiplePartialMatch) matchResponse);
      }
      response.setContentType("text/xml");
      response.getWriter().print(matchResponse.getMessageTwiml());
    } catch (TwiMLException e) {
      throw new RuntimeException(e);
    }
  }

  private void cache(HttpServletResponse response, MultiplePartialMatch matchResponse) {
    Cookie lastQueryCookie =
        new Cookie(LAST_QUERY_COOKIE_NAME,
            matchResponse.getLastQueryOptions());
    response.addCookie(lastQueryCookie);
  }

  private EmployeeMatch getEmployeeMatch(HttpServletRequest request, HttpServletResponse response, String queryValue) {
    List<NameValuePair> optionsOfEmployees =
        Utils.getCookieAndDispose(response, LAST_QUERY_COOKIE_NAME, request.getCookies());
    return employeeDirectoryService.queryEmployee(queryValue, optionsOfEmployees);
  }
}
