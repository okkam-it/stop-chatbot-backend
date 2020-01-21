package com.stop.repository;

import com.stop.model.Bot;
import com.stop.model.BotAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotAddressRepository extends JpaRepository<BotAddress, Long> {

  public BotAddress findOneByBot(Bot bot);
  
}
