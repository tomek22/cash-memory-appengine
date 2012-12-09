package hr.punintended.cashmemory.domain;

import java.util.Date;

import lombok.Getter;

import com.googlecode.objectify.annotation.Index;

public abstract class TimeAwareAbstractEntity {

  @Index
  @Getter
  private Date dateCreated;

  public TimeAwareAbstractEntity() {
    this.dateCreated = new Date();
  }
}
