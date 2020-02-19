package com.stop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class UserBioDataDto implements Comparable<UserBioDataDto> {

  @JsonProperty("heart_rate")
  private int heartRate;
  private Date date;

  public int getHeartRate() {
    return heartRate;
  }

  public void setHeartRate(int heartRate) {
    this.heartRate = heartRate;
  }
  
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public int compareTo(UserBioDataDto o) {
    return date.compareTo(o.getDate());
  }

}
