package hr.punintended.cashmemory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ExpenseState extends DefaultAbstractEntity {

  public static enum Type {
    UNINITIALIZED, INITIALIZED, CLAIMED, CLOSED
  };

  @Parent
  @NonNull
  private Key<Expense> expense;

  @Getter
  @NonNull
  private Type type;

  @Getter
  private Key<AppUser> creator;

  @Getter
  @Setter
  private boolean revoking = false;

}
