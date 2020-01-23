package com.stop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "branch")
public class Branch extends AbstractStopResource {

  private String name;
  private String code;
  @ManyToMany
  private Set<User> users = new HashSet<>();
  @ManyToMany
  private Set<Bot> bots = new HashSet<>();

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
  private List<ChatRoom> chatRoom = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Bot> getBots() {
    return bots;
  }

  public void setBots(Set<Bot> bots) {
    this.bots = bots;
  }

  public List<ChatRoom> getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(List<ChatRoom> chatRoom) {
    this.chatRoom = chatRoom;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
