package com.stop.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bot_address")
public class BotAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  private String ip;
  private int port;
  private String apiPath;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bot_id", nullable = false)
  private Bot bot;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getApiPath() {
    return apiPath;
  }

  public void setApiPath(String apiPath) {
    this.apiPath = apiPath;
  }

  public Bot getBot() {
    return bot;
  }

  public void setBot(Bot bot) {
    this.bot = bot;
  }

}
