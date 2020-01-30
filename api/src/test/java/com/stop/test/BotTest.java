package com.stop.test;

import com.stop.model.Bot;
import com.stop.model.BotAddress;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class BotTest extends BaseRepositoryTest {

  private static final int EDIT_PORT = 5001;

  @Test
  public void getBots() {
    List<Bot> bots = botRepository.findAll();
    Assert.assertTrue(bots.size() > 0);
  }

  @Test
  public void createBot() {
    Bot bot = new Bot();
    bot.setAvailable(true);
    bot.setCreated(new Date());
    bot.setName("Test bot");
    bot.setDescription("A bot created for test purposes");
    bot.setShowTo("ADMIN");
    Bot saved = botRepository.save(bot);
    Assert.assertTrue(saved.getId() > 0);
  }

  @Test
  public void createBotAddress() {
    BotAddress botAddress = new BotAddress();
    botAddress.setIp("localhost");
    botAddress.setPort(5000);
    botAddress.setApiPath("");
    Optional<Bot> bot = botRepository.findById(2L);
    botAddress.setBot(bot.get());
    botAddressRepository.save(botAddress);
  }

  @Test
  public void updateBotAddress() {
    Bot bot = botRepository.findById(2L).get();
    BotAddress address = botAddressRepository.findOneByBot(bot);
    address.setPort(EDIT_PORT);
    BotAddress updated = botAddressRepository.save(address);
    Assert.assertEquals(EDIT_PORT, updated.getPort());
  }

  @Test(expected = NoSuchElementException.class)
  public void deleteBot() {
    Bot toDelete = new Bot();
    toDelete.setAvailable(true);
    toDelete.setCreated(new Date());
    toDelete.setName("To delete bot");
    toDelete.setDescription("A bot created to delete");
    toDelete.setShowTo("ADMIN");
    Bot saved = botRepository.save(toDelete);
    Assert.assertTrue(saved.getId() > 0);
    botRepository.deleteById(saved.getId());
    botRepository.findById(saved.getId()).get();
  }

}
