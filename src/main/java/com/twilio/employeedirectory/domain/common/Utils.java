package com.twilio.employeedirectory.domain.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
   * Converts a cookie specified by its name to some list of {@link NameValuePair} and removes from
   * the browser.
   *
   * @param request The request with the cookies
   * @param response The response used to indicate the deleted cookies
   * @param cookieName Name of the cookie
   * @return Optional of {@link List<NameValuePair>} not <code>null</code>
   */
  public static Optional<List<NameValuePair>> getCookieAndDispose(HttpServletRequest request,
      HttpServletResponse response, String cookieName) {
    Optional<Cookie[]> cookies = Optional.ofNullable(request.getCookies());
    if (cookies.isPresent()) {
      return Arrays.stream(cookies.get()).filter(c -> cookieName.equals(c.getName())).findFirst()
          .map(c -> Utils.createOptionsAndDisposeCookie(c, response)).filter(l -> !l.isEmpty());
    } else {
      return Optional.empty();
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
