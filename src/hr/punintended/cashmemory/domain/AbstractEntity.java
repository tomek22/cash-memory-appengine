package hr.punintended.cashmemory.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

@Entity
@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Key key;

  @Column(nullable = false)
  private Date dateCreated;

  public AbstractEntity() {
    setDateCreated(new Date());
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public Key getKey() {
    return key;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateCreated() {
    return dateCreated;
  }
}
