package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class ExpenseGroup extends DefaultAbstractEntity {

  @Getter
  private String name;

  @Getter
  private String currency;

  @Getter
  private boolean adHoc;

  public ExpenseGroup(String name, String currency, boolean adHoc) {
    super();
    this.name = name;
    this.currency = currency;
    this.adHoc = adHoc;
  }

  public ExpenseGroup(String name, String currency) {
    this(name, currency, false);
  }

}
