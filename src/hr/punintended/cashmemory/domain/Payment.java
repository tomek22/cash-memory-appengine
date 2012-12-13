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
@AllArgsConstructor
@RequiredArgsConstructor
public class Payment extends DefaultAbstractEntity {

  @Parent
  @NonNull
  private Key<Expense> expense;

  @Getter
  @NonNull
  private Key<AppUser> payer;

  @Getter
  @NonNull
  private double amount;

  @NonNull
  @Getter
  private String currency;

  @Getter
  private String imageUrl;

}
