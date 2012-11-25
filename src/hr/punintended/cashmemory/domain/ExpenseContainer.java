package hr.punintended.cashmemory.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@Entity
@MappedSuperclass
public class ExpenseContainer extends AbstractEntity {

  private String currency;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Expense> expenses;

  protected ExpenseContainer(String currency) {
    super();
    this.currency = currency;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<Expense> expenses) {
    this.expenses = expenses;
  }

}
