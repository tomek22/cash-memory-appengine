package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.annotation.Id;

public abstract class DefaultAbstractEntity extends TimeAwareAbstractEntity {

  @Id
  @Getter
  private Long id;

  public DefaultAbstractEntity() {
    super();
  }
}
