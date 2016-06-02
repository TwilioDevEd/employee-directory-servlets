package com.twilio.employeedirectory.domain.repository;

import com.twilio.employeedirectory.domain.model.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Handle {@link Employee} entities in the database
 */
public class EmployeeRepository {

  private final CriteriaBuilder criteriaBuilder;

  private EntityManager entityManager;

  @Inject
  public EmployeeRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.criteriaBuilder = entityManager.getCriteriaBuilder();
  }

  /**
   * Add multiple employees wrapped into some {@link Iterable<Employee>} element
   *
   * @param employees employees to add
   */
  public void addAll(Iterable<Employee> employees) {
    employees.forEach(entityManager::persist);
  }

  /**
   * Add one or more employees
   *
   * @param employees employees to add
   */
  public void add(Employee... employees) {
    addAll(Arrays.asList(employees));
  }

  /**
   * Retrieve all available employees
   *
   * @return A {@link List<Employee>} not <code>null</code>
   */
  public List<Employee> getAll() {
    CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);
    CriteriaQuery<Employee> select = query.select(root);
    return entityManager.createQuery(select).getResultList();
  }

  /**
   * Returns the first {@link Employee} located in the database
   *
   * @return An {@link Optional<Employee>} not <code>null</code>
   */
  public Optional<Employee> findFirstEmployee() {
    CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);
    CriteriaQuery<Employee> select = query.select(root);
    TypedQuery<Employee> employeeQuery = entityManager.createQuery(select);
    try {
      return Optional.ofNullable(employeeQuery.setMaxResults(1).getSingleResult());
    } catch (NoResultException ex) {
      return Optional.empty();
    }
  }

  /**
   * Queries employees by part of their full name
   *
   * @param fullNameQuery Query of the fullname
   */
  public List<Employee> findEmployeeByFullName(String fullNameQuery) {
    CriteriaQuery<Employee> cq = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> employeeRoot = cq.from(Employee.class);
    String likeSyntax = String.format("%%%s%%", fullNameQuery);
    Predicate likeFullName = criteriaBuilder.like(employeeRoot.get("fullName"), likeSyntax);
    CriteriaQuery<Employee> likeCriteriaQuery = cq.where(likeFullName);
    return entityManager.createQuery(likeCriteriaQuery).getResultList();
  }

  /**
   * Gets a {@link Employee} by its <code>id</code>
   *
   * @param id The {@link Employee#id} property value
   * @return An {@link Optional} with the {@link Employee} found or
   *         {@link Optional#empty()}
   */
  public Optional<Employee> findEmployeeById(Long id) {
    return Optional.ofNullable(entityManager.find(Employee.class, id));
  }
}
