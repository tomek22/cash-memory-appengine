package hr.punintended.cashmemory.domain;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class ExpenseState extends DefaultAbstractEntity {

  public static enum Type {
    UNINITIALIZED, INITIALIZED, CLAIMED, CLOSED
  };

  @Parent
  private Key<Expense> expense;

  @Getter
  private Type type;

  @Getter
  private Key<AppUser> creator;

  @Getter @Setter
  private boolean revoking;

  public ExpenseState(Key<Expense> expense, Type type, Key<AppUser> creator,
      boolean revoking) {
    super();
    this.expense = expense;
    this.type = type;
    this.creator = creator;
    this.revoking = revoking;
  }

  public ExpenseState(Key<Expense> expense, Type type, Key<AppUser> creator) {
    this(expense, type, creator, false);
  }

  public ExpenseState(Key<Expense> expense, Type type) {
    this(expense, type, null);
  }

}
