package hr.punintended.cashmemory.domain;

import java.util.Date;

import lombok.Getter;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnSave;

public abstract class TimeAwareAbstractEntity {

  @Index
  @Getter
  private Date dateCreated;

  @Getter
  private Date lastUpdated;

  @OnSave
  void handleTime() {
    this.lastUpdated = new Date();
    if (this.dateCreated == null)
      this.dateCreated = this.lastUpdated;
  }
}
