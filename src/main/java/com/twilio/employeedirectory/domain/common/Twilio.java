package com.twilio.employeedirectory.domain.common;

/**
 * Constants related to Twilio Api and bussiness logic
 */
public class Twilio {

  /**
   * The param used by the Twilio Api with the content (employee) queried by phone
   * @see <a href="https://www.twilio.com/docs/api/twiml/sms/twilio_request#request-parameters">TwiML Message Requests' Parameters</a>
   */
  public static final String QUERY_PARAM = "Body";

}
