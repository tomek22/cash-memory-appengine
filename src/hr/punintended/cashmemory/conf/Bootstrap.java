package hr.punintended.cashmemory.conf;

import hr.punintended.cashmemory.domain.AppUser;
import hr.punintended.cashmemory.domain.Expense;
import hr.punintended.cashmemory.domain.ExpenseGroup;
import hr.punintended.cashmemory.domain.ExpenseState;
import hr.punintended.cashmemory.domain.GroupMembership;
import hr.punintended.cashmemory.domain.Payment;

import com.googlecode.objectify.ObjectifyService;

public class Bootstrap {
  private static boolean initialized = false;

  public static final void init() {
    if (!initialized) {
      ObjectifyService.register(AppUser.class);
      ObjectifyService.register(Expense.class);
      ObjectifyService.register(ExpenseGroup.class);
      ObjectifyService.register(ExpenseState.class);
      ObjectifyService.register(GroupMembership.class);
      ObjectifyService.register(Payment.class);
      initialized = true;
    }
  }
}
