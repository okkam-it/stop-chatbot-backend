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
  @ManyToMany
  private Set<User> users = new HashSet<>();
  @ManyToMany
  private Set<Bot> bots = new HashSet<>();

  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
  private List<ChatRoom> chatroom = new ArrayList<>();

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

  public List<ChatRoom> getChatroom() {
    return chatroom;
  }

  public void setChatroom(List<ChatRoom> chatroom) {
    this.chatroom = chatroom;
  }

}
