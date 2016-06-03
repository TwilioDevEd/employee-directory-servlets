package com.twilio.employeedirectory.application.config;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;
import com.twilio.employeedirectory.application.servlet
        .EmployeeDirectoryServlet;
import com.twilio.employeedirectory.application.servlet.IndexServlet;

/**
 * Configure the servlet scopes for Employees
 */
public class EmployeeServletsGuiceConfig extends ServletModule {

  @Override
  public void configureServlets() {
    filter("/*").through(PersistFilter.class);
    serve("/directory/search").with(EmployeeDirectoryServlet.class);
    serve("/").with(IndexServlet.class);
  }
}
