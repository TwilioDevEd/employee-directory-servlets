package com.twilio.employeedirectory.domain.query;

import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Media;
import com.twilio.twiml.messaging.Message;


/**
 * When it returns an {@link com.twilio.employeedirectory.domain.model.Employee}
 * which name is exactly the one queried.
 */
public class PerfectMatch implements EmployeeMatch {

  private final Employee foundEmployee;

  public PerfectMatch(Employee foundEmployee) {
    this.foundEmployee = foundEmployee;
  }

  @Override
  public String getMessage() {
    return String.format("%s\n%s\n%s", foundEmployee.getFullName(), foundEmployee.getPhoneNumber(),
        foundEmployee.getEmail());
  }

  @Override
  public String getMessageTwiml() throws TwiMLException {
    return new MessagingResponse.Builder()
      .message(new Message.Builder()
        .body(new Body.Builder(getMessage()).build())
        .media(new Media.Builder(foundEmployee.getImageUrl()).build())
        .build()
      )
      .build()
      .toXml();
  }

  public Employee getFoundEmployee() {
    return foundEmployee;
  }

  @Override
  public boolean isSingleEmployeeFound() {
    return true;
  }
}
