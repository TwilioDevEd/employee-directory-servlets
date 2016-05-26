package com.twilio.employeedirectory.application.config;

import com.google.inject.AbstractModule;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryService;
import com.twilio.employeedirectory.domain.service.EmployeeDirectoryServiceImpl;

/**
 * Define bussiness logic initialization
 */
public class EmployeeModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(EmployeeDirectoryService.class).to(EmployeeDirectoryServiceImpl.class);
  }
}
