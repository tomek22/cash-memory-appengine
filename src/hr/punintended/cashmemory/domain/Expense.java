package hr.punintended.cashmemory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Expense extends DefaultAbstractEntity {

  @Parent
  @NonNull
  @Getter
  private Key<ExpenseGroup> group;

  @NonNull
  @Getter
  private int amount;

  @NonNull
  @Getter
  private String currency;

  @Getter
  private Key<AppUser> creator;

  @Getter
  private String name;

}
