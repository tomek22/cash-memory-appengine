package hr.punintended.cashmemory.domain;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

@Entity
public class GroupMembership extends AbstractEntity {

  private Key appUser;

  private Double balance;

  public GroupMembership(Key appUser) {
    this.appUser = appUser;
    this.balance = 0.d;
  }

  public Key getAppUser() {
    return appUser;
  }

  public void setAppUser(Key appUser) {
    this.appUser = appUser;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

}
