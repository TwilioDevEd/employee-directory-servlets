package com.twilio.employeedirectory.domain.query;

import com.twilio.employeedirectory.domain.model.Employee;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * When the response returns multiple results which are not perfect match of the queried name
 */
public class MultiplePartialMatch implements EmployeeMatch {

  private final List<Employee> foundEmployees;

  private String lastQueryId;

  public MultiplePartialMatch(List<Employee> foundEmployees) {
    this.foundEmployees = foundEmployees;
  }

  @Override
  public String getMessage() {
    StringBuilder builder = new StringBuilder();
    IntStream.rangeClosed(1, foundEmployees.size()).boxed()
        .map(i -> String.format("%d %s\n", i, foundEmployees.get(i - 1))).forEach(builder::append);
    return builder.toString();
  }

  public List<Employee> getFoundEmployees() {
    return foundEmployees;
  }


  /**
   * Returns some string which the url query of the options given by this {@linnk EmployeeMatch}
   * 
   * @return {@link String} not <code>null</code>
   */
  public String getLastQueryOptions() {
    if (lastQueryId == null) {
      List<BasicNameValuePair> params = new LinkedList<>();
      IntStream
          .rangeClosed(1, foundEmployees.size())
          .boxed()
          .map(
              i -> new BasicNameValuePair(i.toString(), foundEmployees.get(i - 1).getId()
                  .toString())).forEach(params::add);
      lastQueryId = URLEncodedUtils.format(params, StandardCharsets.US_ASCII);
    }
    return lastQueryId;
  }

}
