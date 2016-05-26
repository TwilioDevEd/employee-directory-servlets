package com.twilio.employeedirectory.domain.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.twilio.employeedirectory.domain.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryTest {

  private EmployeeRepository employeeRepository;

  private IntegrationTestHelper integrationTestHelper;

  @Before
  public void setUp() throws Exception {
    Injector injector = Guice.createInjector(new JpaPersistModule("jpa-employee-test"));
    initPersistService(injector);
    employeeRepository = injector.getInstance(EmployeeRepository.class);
    integrationTestHelper = injector.getInstance(IntegrationTestHelper.class);
    integrationTestHelper.cleanTable(Employee.class);
  }

  private void initPersistService(Injector injector) {
    PersistService instance = injector.getInstance(PersistService.class);
    instance.start();
  }

  public void populateDatabase(Employee... employees) {
    integrationTestHelper.startTransaction();
    employeeRepository.add(employees);
    integrationTestHelper.finishTransaction();
  }

  @Test
  public void testAddAll() throws Exception {
    populateDatabase(new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"), new Employee("Iron Man",
        "ironMan@heroes.example.com", "+14155559368",
        "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg"));
    List<Employee> allEmployee = employeeRepository.getAll();
    Assert.assertEquals("There are 2 employees", allEmployee.size(), 2);
  }

  @Test
  public void shouldNotBeEmployeesInDatabase() {
    Assert.assertTrue("Found some employees", employeeRepository.getAll().isEmpty());
  }

  @Test
  public void testFindFirstEmployee() throws Exception {
    populateDatabase(new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"));
    Optional<Employee> firstEmployee = employeeRepository.findFirstEmployee();
    Assert.assertTrue("First employee not present", firstEmployee.isPresent());
    Assert.assertEquals("The first employee is not Spider-Man", firstEmployee.get().getFullName(),
        "Spider-Man");
  }

  @Test
  public void shouldIndicateEmptyWhenTheresNoEmployeeAtTheDatabase() {
    Optional<Employee> firstEmployee = employeeRepository.findFirstEmployee();
    Assert.assertFalse("Found a first employee at the database", firstEmployee.isPresent());
  }

  @Test
  public void shouldReturnBothEmployees() {
    populateDatabase(new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"), new Employee("Iron Man",
        "ironMan@heroes.example.com", "+14155559368",
        "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg"));
    List<Employee> foundEmployees = employeeRepository.findEmployeeByFullName("Man");
    Assert.assertEquals("The query didn't included both Heroes", 2, foundEmployees.size());
  }

  @Test
  public void shouldReturnOneEmployee() {
    populateDatabase(
            new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"),
            new Employee("Iron Man", "ironMan@heroes.example.com", "+14155559368",
        "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg")
    );

    List<Employee> foundEmployees = employeeRepository.findEmployeeByFullName("Spide");
    Assert.assertEquals("The query didn't included one heroe", 1, foundEmployees.size());
    Assert.assertEquals("The query didn't included Spider-Man", "spider-man@heroes.example.com",
        foundEmployees.get(0).getEmail());
  }

  @Test
  public void shouldReturnNoEmployee() {
    populateDatabase(new Employee("Spider-Man", "spider-man@heroes.example.com", "+14155559610",
        "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b.jpg"), new Employee("Iron Man",
        "ironMan@heroes.example.com", "+14155559368",
        "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg"));
    List<Employee> foundEmployees = employeeRepository.findEmployeeByFullName("Spidez");
    Assert.assertTrue("Found some wrong heroe", foundEmployees.isEmpty());
  }

  @After
  public void tearDown() throws Exception {

  }
}
