package com.stop.dto;

import java.util.Date;

public class UserBioDataDto {
  
  private int heartRate;
  private Date relevationDate;

  public int getHeartRate() {
    return heartRate;
  }

  public void setHeartRate(int heartRate) {
    this.heartRate = heartRate;
  }

  public Date getRelevationDate() {
    return relevationDate;
  }

  public void setRelevationDate(Date relevationDate) {
    this.relevationDate = relevationDate;
  }

}
