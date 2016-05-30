package com.twilio.employeedirectory.application.servlet;

import com.twilio.employeedirectory.domain.common.Twilio;
import com.twilio.employeedirectory.domain.matchers.EmployeeMatch;
import com.twilio.employeedirectory.domain.matchers.NoMatch;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.sdk.verbs.TwiMLException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Singleton
public class EmployeeLookupServlet extends HttpServlet {

    private EmployeeDirectoryService employeeDirectoryService;

    @Inject
    public EmployeeLookupServlet(EmployeeDirectoryService employeeDirectoryService) {
        this.employeeDirectoryService = employeeDirectoryService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<String> fullNameQuery = Optional.ofNullable(request.getParameter(Twilio.QUERY_PARAM));
        final EmployeeMatch matchResponse;
        if (fullNameQuery.isPresent()) {
            matchResponse = employeeDirectoryService.queryEmployee(fullNameQuery.get());
        } else {
            matchResponse = new NoMatch();
        }
        try {
            response.setContentType("text/xml");
            response.getWriter().print(matchResponse.getMessageTwiml());
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }
}
