package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class GroupMembership extends TimeAwareAbstractEntity {

  @Parent
  @Getter
  private Key<ExpenseGroup> group;

  @Id
  @Getter
  private String appUserId;

  @Getter
  private double balance;

  public GroupMembership(Key<ExpenseGroup> group, String appUserId) {
    super();
    this.group = group;
    this.appUserId = appUserId;
    this.balance = 0.d;
  }

  public double changeBalance(double change) {
    return this.balance += change;
  }

}
