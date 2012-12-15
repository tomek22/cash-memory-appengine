package hr.punintended.cashmemory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class GroupMembership extends TimeAwareAbstractEntity {

  @Parent
  @NonNull
  @Getter
  private Key<ExpenseGroup> group;

  @Id
  @NonNull
  @Getter
  private String appUserId;

  @Getter
  private int balance = 0;

  public int changeBalance(int change) {
    return this.balance += change;
  }

}
