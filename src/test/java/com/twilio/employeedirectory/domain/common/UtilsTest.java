package com.twilio.employeedirectory.domain.common;

import com.twilio.employeedirectory.application.servlet.EmployeeLookupServlet;
import org.apache.http.NameValuePair;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UtilsTest {

  @Test
  public void testGetCookieAndDispose() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    Cookie cookie = new Cookie(EmployeeLookupServlet.LAST_QUERY_COOKIE_NAME, "1=21&2=38&3=54");
    when(request.getCookies()).thenReturn(new Cookie[] {cookie});
    List<NameValuePair> options =
        Utils.getCookieAndDispose(request, response, EmployeeLookupServlet.LAST_QUERY_COOKIE_NAME);
    Assert.assertEquals("The count of options is wrong", options.size(), 3);
  }

  @Test
  public void testConvertCookieAndDispose() throws Exception {
    HttpServletResponse response = mock(HttpServletResponse.class);
    Cookie cookie = new Cookie(EmployeeLookupServlet.LAST_QUERY_COOKIE_NAME, "1=21&2=38&3=54");
    List<NameValuePair> nameValuePairs = Utils.convertCookieAndDispose(cookie, response);
    Assert.assertEquals("The count of options is wrong", nameValuePairs.size(), 3);
    Iterator<NameValuePair> valuesIterator = nameValuePairs.iterator();
    NameValuePair currentVal = valuesIterator.next();
    Assert.assertEquals("First element is not with index 1", currentVal.getName(), "1");
    Assert.assertEquals("First element has not the right value", currentVal.getValue(), "21");

    valuesIterator.next();
    currentVal = valuesIterator.next();
    Assert.assertEquals("Last element has not the index 3", currentVal.getName(), "3");
    Assert.assertEquals("Last element has not the right value", currentVal.getValue(), "54");
  }
}
