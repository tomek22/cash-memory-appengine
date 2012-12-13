package hr.punintended.cashmemory.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUser extends TimeAwareAbstractEntity {

  @Id
  @NonNull
  @Getter
  private String email;

  @Getter
  @Setter
  private String deviceRegistrationId;

}
