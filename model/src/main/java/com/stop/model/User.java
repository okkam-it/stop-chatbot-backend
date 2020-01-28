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

  private String uid;
  private String username;
  private String email;
  private boolean admin;

  @ManyToMany
  private Set<Branch> branches = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<ChatRoom> chatRoom = new ArrayList<>();

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

  public List<ChatRoom> getChatRoom() {
    return chatRoom;
  }

  public void setChatRoom(List<ChatRoom> chatRoom) {
    this.chatRoom = chatRoom;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
