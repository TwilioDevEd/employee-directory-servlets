package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.common.Twilio;
import com.twilio.employeedirectory.domain.common.Utils;
import com.twilio.employeedirectory.domain.error.EmployeeLoadException;
import com.twilio.employeedirectory.domain.query.EmployeeMatch;
import com.twilio.employeedirectory.domain.query.MultipleMatch;
import com.twilio.employeedirectory.domain.query.NoMatch;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class EmployeeDirectoryServlet extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(EmployeeDirectoryServlet.class.getName());

  public static final String SUGGESTIONS_COOKIE_NAME = "last-query";

  private final EmployeeDirectoryService employeeDirectoryService;

  @Inject
  public EmployeeDirectoryServlet(final EmployeeDirectoryService employeeDirectoryService) {
    this.employeeDirectoryService = employeeDirectoryService;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Optional<String> fullNameQuery = Optional.ofNullable(request.getParameter(Twilio.QUERY_PARAM));
    try {
      EmployeeMatch matchResponse =
          fullNameQuery
            .map(queryValue -> createEmployeeMatchFromRequest(request, response, queryValue))
            .orElse(new NoMatch());
      request.setAttribute("employeeMatch", matchResponse);
      printMatch(response, matchResponse);
    } catch (EmployeeLoadException ex) {
      printError(response, ex.getMessage());
    }
  }

  private void saveEmployeeSuggestionsIntoCookie(MultipleMatch matchResponse,
                                                 HttpServletResponse response) {
    Cookie lastQueryCookie =
      new Cookie(SUGGESTIONS_COOKIE_NAME, matchResponse.getEmployeeSuggestions());
    response.addCookie(lastQueryCookie);
  }

  private EmployeeMatch createEmployeeMatchFromRequest(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       String queryValue) {
    List<NameValuePair> employeeSuggestions =
      Utils.getCookieAndDispose(response, SUGGESTIONS_COOKIE_NAME, request.getCookies());
    return employeeDirectoryService.queryEmployee(queryValue, employeeSuggestions);
  }

  private void printMatch(HttpServletResponse response, EmployeeMatch matchResponse) {
    try {
      // Only MultiplePartialMatch caches its response in a cookie
      if (matchResponse instanceof MultipleMatch) {
        saveEmployeeSuggestionsIntoCookie((MultipleMatch) matchResponse, response);
      }
      response.setContentType("text/xml");
      response.getWriter().print(matchResponse.getMessageTwiml());
    } catch (TwiMLException e) {
      throw new EmployeeLoadException("Invalid TwiML response");
    } catch (IOException e) {
      throw new EmployeeLoadException("Error writing response in the server");
    }
  }

  private void printError(HttpServletResponse response, String message) {
    try {
      response.setContentType("text/xml");
      TwiMLResponse twiMLResponse = new TwiMLResponse();
      twiMLResponse.append(new Message(message));
      response.getWriter().print(twiMLResponse.toEscapedXML());
    } catch (IOException e) {
      LOG.log(Level.SEVERE, "Error trying to print a text message in the servlet");
    } catch (TwiMLException e) {
      LOG.log(Level.SEVERE, "Error trying to print a TwiML message in the servlet");
    }
  }
}
