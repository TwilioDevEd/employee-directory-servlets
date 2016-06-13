package com.twilio.employeedirectory.domain.query;

import com.twilio.employeedirectory.domain.model.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class MultipleMatchTest {

  @Test
  public void testGetLastQueryId() throws Exception {
    Employee spiderMan =
        new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
            "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg");
    spiderMan.setId(12L);
    Employee ironMan =
        new Employee("Iron Man", "ironMan@heroes.example.com", "+14155559368",
            "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg");
    ironMan.setId(21L);
    MultipleMatch match = new MultipleMatch(Arrays.asList(spiderMan, ironMan));
    Assert.assertEquals("The query str didnt was the expected", "1=12&2=21",
        match.getEmployeeSuggestions());
  }
}
