package hr.punintended.cashmemory.domain;

import javax.persistence.Entity;

@Entity
public class AppUser extends ExpenseContainer {

  private String email;

  private String deviceRegistrationId;

  public AppUser(String email, String currency) {
    super(currency);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDeviceRegistrationId() {
    return deviceRegistrationId;
  }

  public void setDeviceRegistrationId(String deviceRegistrationId) {
    this.deviceRegistrationId = deviceRegistrationId;
  }

}
