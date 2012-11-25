package hr.punintended.cashmemory.domain;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

@Entity
public class Payment extends AbstractEntity {

  private Key payer;

  private Double amount;

  private String imageUrl;

  public Key getPayer() {
    return payer;
  }

  public void setPayer(Key payer) {
    this.payer = payer;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}
