package com.twilio.employeedirectory.domain.query;

import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

/**
 * Defines some response obtained from querying a person's name
 */
public interface EmployeeMatch {

    /**
     * Gets a verbose message to return to the user
     * @return {@link String} not <code>null</code>
     */
    public String getMessage();

    /**
     * Gets the message in Twiml format
     * @return {@link String} not <code>null</code>
     */
    public default String getMessageTwiml() throws TwiMLException
    {
        TwiMLResponse twiMLResponse = new TwiMLResponse();
        twiMLResponse.append(new Message(getMessage()));
        return twiMLResponse.toEscapedXML();
    }

    /**
     * Indicates if the Match returned a unique Employee. So some function <code>getFoundEmployee</code> may be used.
     * @return true|false if There is some unique response.
     */
    public default boolean isSingleEmployeeFound() {
        return false;
    }
}
