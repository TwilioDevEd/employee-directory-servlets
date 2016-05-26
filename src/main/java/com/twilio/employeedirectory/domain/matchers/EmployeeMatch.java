package com.twilio.employeedirectory.domain.matchers;

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
     * Indicates if the Match returned a unique Employee. So some function <code>getFoundEmployee</code> may be used.
     * @return true|false if There is some unique response.
     */
    public default boolean isSingleEmployeeFound() {
        return false;
    }
}
