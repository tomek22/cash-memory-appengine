package hr.punintended.cashmemory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ExpenseGroup extends DefaultAbstractEntity {

  @NonNull
  @Getter
  private String name;

  @Getter
  @Setter
  private Key<AppUser> creator;

  @Getter
  @Setter
  private boolean adHoc = false;

}
