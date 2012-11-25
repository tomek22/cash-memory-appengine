package hr.punintended.cashmemory.domain;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

@Entity
public class ExpenseState extends AbstractEntity {

  public final static Integer UNINITIALIZED = 0;
  public final static Integer INITIALIZED = 1;
  public final static Integer CLAIMED = 2;
  public final static Integer CLOSED = 3;

  private Integer type;

  private Boolean revoking;

  private Key creator;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Boolean getRevoking() {
    return revoking;
  }

  public void setRevoking(Boolean revoking) {
    this.revoking = revoking;
  }

  public Key getCreator() {
    return creator;
  }

  public void setCreator(Key creator) {
    this.creator = creator;
  }

}
