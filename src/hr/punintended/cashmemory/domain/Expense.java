package hr.punintended.cashmemory.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

@Entity
public class Expense extends AbstractEntity {

  private String name;

  private Key template;

  private Key creator;

  private Double amount;

  @OneToMany(cascade = CascadeType.ALL)
  private List<ExpenseState> history;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Payment> payments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Key getTemplate() {
    return template;
  }

  public void setTemplate(Key template) {
    this.template = template;
  }

  public Key getCreator() {
    return creator;
  }

  public void setCreator(Key creator) {
    this.creator = creator;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public List<ExpenseState> getHistory() {
    return history;
  }

  public void setHistory(List<ExpenseState> history) {
    this.history = history;
  }

  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }

}
