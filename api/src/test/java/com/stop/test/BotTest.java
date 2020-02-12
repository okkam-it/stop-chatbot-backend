package com.stop.test;

import com.stop.dto.BotAddressDto;
import com.stop.dto.BotDto;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;

public class BotTest extends BaseRepositoryTest {

  private static final int EDIT_PORT = 5001;

  @Test
  public void findBotById() {
    BotDto bot = botService.findById(2L);
    Assert.assertTrue(bot != null);
  }

  @Test
  public void getBots() {
    List<BotDto> bots = botService.listAllBots();
    Assert.assertTrue(bots.size() > 0);
  }

  @Test
  public void createBot() {
    BotDto bot = new BotDto();
    bot.setAvailable(true);
    bot.setName("Test bot");
    bot.setDescription("A bot created for test purposes");
    bot.setShowTo("ADMIN");
    bot.setAddress(new BotAddressDto());
    BotDto saved = botService.createBot(bot);
    Assert.assertTrue(saved.getId() > 0);
  }

  @Test
  public void createBotAddress() {
    BotAddressDto botAddress = new BotAddressDto();
    botAddress.setIp("localhost");
    botAddress.setPort(5000);
    botAddress.setPath("");
    BotDto bot = botService.findById(2L);
    bot.setAddress(botAddress);
    botService.updateBot(2L, bot);
  }

  @Test
  public void updateBotAddress() {
    BotDto bot = botService.findById(2L);
    BotAddressDto address = bot.getAddress();
    address.setPort(EDIT_PORT);
    BotDto updated = botService.updateBot(2L, bot);
    Assert.assertEquals(EDIT_PORT, updated.getAddress().getPort());
  }

  @Test(expected = ResponseStatusException.class)
  public void deleteBot() {
    BotDto toDelete = new BotDto();
    toDelete.setAvailable(true);
    toDelete.setName("To delete bot");
    toDelete.setDescription("A bot created to delete");
    toDelete.setShowTo("ADMIN");
    BotDto saved = botService.createBot(toDelete);
    Assert.assertTrue(saved.getId() > 0);
    botService.delete(saved.getId());
    botService.findById(saved.getId());
  }

}
