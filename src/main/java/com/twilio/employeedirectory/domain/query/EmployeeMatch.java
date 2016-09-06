package com.twilio.employeedirectory.domain.query;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;


/**
 * Defines some response obtained from querying a person's name
 */
public interface EmployeeMatch {

  /**
   * Gets a verbose message to return to the user
   *
   * @return {@link String} not <code>null</code>
   */
  String getMessage();

  /**
   * Gets the message in Twiml format
   *
   * @return {@link String} not <code>null</code>
   * @throws TwiMLException in case of invalid TwiML Verb
   */
  default String getMessageTwiml() throws TwiMLException {
    return new MessagingResponse.Builder()
      .message(new Message.Builder()
        .body(new Body(getMessage()))
        .build()
      )
      .build()
      .toXml();
  }

  /**
   * Indicates if the Match returned a unique Employee. So some function
   * <code>getFoundEmployee</code> may be used.
   *
   * @return true|false if There is some unique response.
   */
  default boolean isSingleEmployeeFound() {
    return false;
  }
}
