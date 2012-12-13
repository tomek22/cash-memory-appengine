package hr.punintended.cashmemory.domain;

import lombok.Getter;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.OnSave;

public abstract class DefaultAbstractEntity extends TimeAwareAbstractEntity {

  @Id
  @Getter
  private Long id;

  @Ignore
  private boolean loaded = false;

  @OnLoad
  void logLoad() {
    this.loaded = true;
  }

  @OnSave
  void protectExisting() {
    if (!loaded)
      this.id = null;
  }
}
