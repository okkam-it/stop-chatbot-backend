package com.stop.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bot")
public class Bot extends AbstractStopResource {

  private boolean available;
  private String name;
  private String description;
  private String showTo;

  @OneToOne(mappedBy = "bot")
  private BotAddress botAddress;

  @ManyToMany
  private Set<Branch> branches;

  @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL)
  private List<ChatRoom> chatRoom;

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getShowTo() {
    return showTo;
  }

  public void setShowTo(String showTo) {
    this.showTo = showTo;
  }

  public BotAddress getBotAddress() {
    return botAddress;
  }

  public void setBotAddress(BotAddress botAddress) {
    this.botAddress = botAddress;
  }

  public Set<Branch> getBranches() {
    return branches;
  }

  public void setBranches(Set<Branch> branches) {
    this.branches = branches;
  }

  public List<ChatRoom> getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(List<ChatRoom> chatRoom) {
    this.chatRoom = chatRoom;
  }

}
