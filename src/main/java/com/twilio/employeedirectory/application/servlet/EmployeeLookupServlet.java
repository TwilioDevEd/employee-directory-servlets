package com.twilio.employeedirectory.application.servlet;

import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class EmployeeLookupServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
      throws IOException {

    /*Gather gather = new Gather();
    gather.setAction("/menu/show");
    gather.setNumDigits(1);

    Play play = new Play("http://howtodocs.s3.amazonaws.com/et-phone.mp3");
    play.setLoop(3);

    try {
      gather.append(play);
    } catch (TwiMLException e) {
      e.printStackTrace();
    }

    TwiMLResponse twiMLResponse = new TwiMLResponse();
    try {
      twiMLResponse.append(gather);
    } catch (TwiMLException e) {
      e.printStackTrace();
    }

    servletResponse.setContentType("text/xml");
    servletResponse.getWriter().write(twiMLResponse.toXML());*/
  }
}
