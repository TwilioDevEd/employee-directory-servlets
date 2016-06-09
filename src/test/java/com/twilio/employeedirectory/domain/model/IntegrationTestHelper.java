package com.twilio.employeedirectory.domain.model;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class IntegrationTestHelper {

  private EntityManager entityManager;

  @Inject
  public IntegrationTestHelper(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void finishTransaction() {
    this.entityManager.getTransaction().commit();
  }

  public void startTransaction() {
    this.entityManager.getTransaction().begin();
  }

  public <T> void cleanTable(Class<T> clazz) {
    this.entityManager.getTransaction().begin();
    String deleteStatement = String.format("delete from %s", clazz.getSimpleName());
    this.entityManager.createQuery(deleteStatement).executeUpdate();
    this.entityManager.getTransaction().commit();
  }

}
