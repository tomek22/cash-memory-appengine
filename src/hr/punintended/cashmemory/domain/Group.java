package hr.punintended.cashmemory.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Group extends ExpenseContainer {

  private String name;

  private Boolean adHoc;

  @OneToMany(cascade = CascadeType.ALL)
  private List<GroupMembership> memberships;

  public Group(String name, String currency, boolean adHoc) {
    super(currency);
    this.name = name;
    this.adHoc = adHoc;
  }

  public Group(String name, String currency) {
    this(name, currency, false);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getAdHoc() {
    return adHoc;
  }

  public void setAdHoc(Boolean adHoc) {
    this.adHoc = adHoc;
  }

  public List<GroupMembership> getMemberships() {
    return memberships;
  }

  public void setMemberships(List<GroupMembership> memberships) {
    this.memberships = memberships;
  }

}
