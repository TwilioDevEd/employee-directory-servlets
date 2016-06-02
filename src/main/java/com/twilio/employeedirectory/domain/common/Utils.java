package com.twilio.employeedirectory.domain.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Complementary functions
 */
public class Utils {

  private Utils() {}

  /**
   * Returns an Long from the string or returns {@link Optional#empty()}
   *
   * @param text Text which may contains some {@link Long}
   * @return {@link Optional} not <code>null</code>
   */
  public static Optional<Long> getOptionalLong(String text) {
    try {
      return Optional.of(Long.parseLong(text));
    } catch (NumberFormatException ex) {
      return Optional.empty();
    }
  }

  /**
   * Converts a cookie specified by its name to some list of
   * {@link NameValuePair} and removes from the browser.
   *
   * @param response The response used to indicate the deleted cookies
   * @param cookieName Name of the cookie
   * @param cookies
   * @return Optional of {@link List<NameValuePair>} not <code>null</code>
   */
  public static List<NameValuePair> getCookieAndDispose(HttpServletResponse response,
                                          String cookieName, Cookie[] cookies) {
    if (cookies != null) {
      Stream<Cookie> filteredByName = Arrays.stream(cookies)
              .filter(c -> cookieName.equals(c.getName()));
      Optional<Cookie> firstCookie = filteredByName.findFirst();
      return firstCookie.map(c -> Utils.createOptionsAndDisposeCookie(c, response))
              .orElse(Collections.emptyList());
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * Convert a cookie to some {@link NameValuePair} list and removes it
   * 
   * @param cookie Instance of cookie not <code>null</code>
   * @param response The response used to indicate the deleted cookies
   * @return {@link List<NameValuePair>} not <code>null</code>
   */
  public static List<NameValuePair> createOptionsAndDisposeCookie(Cookie cookie,
                                                                  HttpServletResponse response) {
    String currentValue = cookie.getValue();
    cookie.setMaxAge(0);
    cookie.setValue("");
    cookie.setPath("/");
    response.addCookie(cookie);
    return URLEncodedUtils.parse(currentValue, StandardCharsets.US_ASCII);
  }
}
