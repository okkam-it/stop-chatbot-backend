package com.stop.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_bio_data")
public class UserBioData extends AbstractStopResource {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private int heartRate;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getHeartRate() {
    return heartRate;
  }

  public void setHeartRate(int heartRate) {
    this.heartRate = heartRate;
  }

}
