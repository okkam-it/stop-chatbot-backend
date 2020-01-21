package com.stop.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "chatroom")
public class ChatRoom extends AbstractStopResource {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bot_id", nullable = false)
  private Bot bot;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "branch_id", nullable = false)
  private Branch branch;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  private boolean typing;
  
  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
  private List<Chat> chats;

  public Bot getBot() {
    return bot;
  }

  public void setBot(Bot bot) {
    this.bot = bot;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean isTyping() {
    return typing;
  }

  public void setTyping(boolean typing) {
    this.typing = typing;
  }

  public List<Chat> getChats() {
    return chats;
  }

  public void setChats(List<Chat> chats) {
    this.chats = chats;
  }
  
}
