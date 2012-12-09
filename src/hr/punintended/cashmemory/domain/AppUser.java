package hr.punintended.cashmemory.domain;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class AppUser extends TimeAwareAbstractEntity {

  @Id
  @Getter
  private String email;

  @Getter
  @Setter
  private String deviceRegistrationId;

  public AppUser(String email) {
    super();
    this.email = email;
  }

}
