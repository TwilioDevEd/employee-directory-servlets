package com.twilio.employeedirectory.domain.matchers;

import com.twilio.employeedirectory.domain.model.Employee;
import com.twilio.sdk.verbs.*;

/**
 * When it returns an {@link com.twilio.employeedirectory.domain.model.Employee} which name is
 * exactly the one queried.
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
    public TwiMLResponse getTwiMLResponse() throws TwiMLException {
        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Message message = new Message();
        message.append(new Body(getMessage()));
        message.append(new Media(foundEmployee.getImageUrl()));
        twiMLResponse.append(message);
        return twiMLResponse;
    }

    public Employee getFoundEmployee() {
        return foundEmployee;
    }

    @Override
    public boolean isSingleEmployeeFound() {
        return true;
    }
}
