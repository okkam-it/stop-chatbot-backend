package com.stop.dto;

import java.util.ArrayList;
import java.util.List;

public class BranchDto {

  private Long id;
  private String name;
  private String code;
  private List<Long> bots = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<Long> getBots() {
    return bots;
  }

  public void setBots(List<Long> bots) {
    this.bots = bots;
  }

}
