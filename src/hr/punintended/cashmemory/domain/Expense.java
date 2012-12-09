package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Expense extends DefaultAbstractEntity {

  @Parent
  @Getter
  private Key<ExpenseGroup> group;

  @Getter
  private Key<AppUser> creator;

  @Getter
  private String name;

  @Getter
  private int amount;

  public Expense(Key<ExpenseGroup> group, int amount, String name,
      Key<AppUser> creator) {
    super();
    this.group = group;
    this.name = name;
    this.amount = amount;
    this.creator = creator;
  }

  public Expense(Key<ExpenseGroup> group, int amount, String name) {
    this(group, amount, name, null);
  }

  public Expense(Key<ExpenseGroup> group, int amount) {
    this(group, amount, null);
  }

}
