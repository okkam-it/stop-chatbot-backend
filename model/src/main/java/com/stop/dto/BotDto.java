package com.stop.dto;

public class BotDto {

  private Long id;
  private String name;
  private String description;
  private String showTo;
  private BotAddressDto address;

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

  public String getShowTo() {
    return showTo;
  }

  public void setShowTo(String showTo) {
    this.showTo = showTo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BotAddressDto getAddress() {
    return address;
  }

  public void setAddress(BotAddressDto address) {
    this.address = address;
  }
}
