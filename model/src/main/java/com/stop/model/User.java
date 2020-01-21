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
@Table(name = "user")
public class User extends AbstractStopResource {

  private String name;
  private boolean admin;

  @ManyToMany
  private Set<Branch> branches = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ChatRoom> chatroom = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public Set<Branch> getBranches() {
    return branches;
  }

  public void setBranches(Set<Branch> branches) {
    this.branches = branches;
  }

  public List<ChatRoom> getChatroom() {
    return chatroom;
  }

  public void setChatroom(List<ChatRoom> chatroom) {
    this.chatroom = chatroom;
  }

}
