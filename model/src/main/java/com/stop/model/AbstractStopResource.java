package com.stop.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractStopResource {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(name = "created", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date created = new Date();

  @Column(name = "last_edit")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastEdit = new Date();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastEdit() {
    return lastEdit;
  }

  public void setLastEdit(Date lastEdit) {
    this.lastEdit = lastEdit;
  }

}
