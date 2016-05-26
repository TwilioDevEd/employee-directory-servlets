package com.twilio.employeedirectory.domain.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Long id;

  @JsonProperty("fullname")
  private String fullName;

  private String email;

  @JsonProperty("phonenumber")
  private String phoneNumber;

  @JsonProperty("imageurl")
  private String ImageUrl;

  private Employee() { /* needed by the ORM */}

  public Employee(String fullName, String email, String phoneNumber, String imageUrl) {
    this.fullName = fullName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    ImageUrl = imageUrl;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getImageUrl() {
    return ImageUrl;
  }

  public void setImageUrl(String imageUrl) {
    ImageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return getFullName();
  }
}
