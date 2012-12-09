package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Payment extends DefaultAbstractEntity {

  @Parent
  private Key<Expense> expense;

  @Getter
  private Key<AppUser> payer;

  @Getter
  private double amount;

  @Getter
  private String imageUrl;

  public Payment(Key<Expense> expense, Key<AppUser> payer, double amount,
      String imageUrl) {
    super();
    this.expense = expense;
    this.payer = payer;
    this.amount = amount;
    this.imageUrl = imageUrl;
  }

  public Payment(Key<Expense> expense, Key<AppUser> payer, double amount) {
    this(expense, payer, amount, null);
  }

}
