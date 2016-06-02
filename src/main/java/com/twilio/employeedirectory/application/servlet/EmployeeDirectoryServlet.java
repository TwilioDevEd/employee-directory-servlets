package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.common.Twilio;
import com.twilio.employeedirectory.domain.common.Utils;
import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultipleMatch;
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
public class EmployeeDirectoryServlet extends HttpServlet {

  public static final String SUGGESTIONS_COOKIE_NAME = "last-query";

  private final EmployeeDirectoryService employeeDirectoryService;

  @Inject
  public EmployeeDirectoryServlet(final EmployeeDirectoryService employeeDirectoryService) {
    this.employeeDirectoryService = employeeDirectoryService;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Optional<String> fullNameQuery = Optional.ofNullable(request.getParameter(Twilio.QUERY_PARAM));
    EmployeeMatch matchResponse =
        fullNameQuery.map(
            queryValue -> createEmployeeMatchFromRequest(request, response, queryValue)).orElse(
            new NoMatch());
    request.setAttribute("employeeMatch", matchResponse);
    printMatch(response, matchResponse);
  }

  private void printMatch(HttpServletResponse response, EmployeeMatch matchResponse)
      throws IOException {
    try {
      // Only MultiplePartialMatch caches its response in a cookie
      if (matchResponse instanceof MultipleMatch) {
        saveEmployeeSuggestionsIntoCookie((MultipleMatch) matchResponse, response);
      }
      response.setContentType("text/xml");
      response.getWriter().print(matchResponse.getMessageTwiml());
    } catch (TwiMLException e) {
      throw new RuntimeException(e);
    }
  }

  private void saveEmployeeSuggestionsIntoCookie(MultipleMatch matchResponse,
      HttpServletResponse response) {
    Cookie lastQueryCookie =
        new Cookie(SUGGESTIONS_COOKIE_NAME, matchResponse.getEmployeeSuggestions());
    response.addCookie(lastQueryCookie);
  }

  private EmployeeMatch createEmployeeMatchFromRequest(HttpServletRequest request,
      HttpServletResponse response, String queryValue) {
    List<NameValuePair> employeeSuggestions =
        Utils.getCookieAndDispose(response, SUGGESTIONS_COOKIE_NAME, request.getCookies());
    return employeeDirectoryService.queryEmployee(queryValue, employeeSuggestions);
  }
}
