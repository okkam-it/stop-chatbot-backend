package com.stop.api.task;

import com.stop.api.service.BotService;
import com.stop.dto.BotAddressDto;
import com.stop.dto.BotDto;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class BotAvailabilityTimerTask {

  private static final Logger LOG = LoggerFactory.getLogger(BotAvailabilityTimerTask.class);

  @Autowired
  private BotService botService;

  @Autowired
  private RestTemplate restTemplate;

  /**
   * Run check on bots availability. Every 10 seconds check if the bots are available. Task starts
   * after 30 seconds.
   */
  @Scheduled(fixedRate = 10_000, initialDelay = 30_000)
  public void run() {
    List<BotDto> bots = botService.listAllBots();
    for (BotDto bot : bots) {
      BotAddressDto addressDto = bot.getAddress();
      String url = String.format("http://%s:%s", addressDto.getIp(), addressDto.getPort());
      boolean available = botAvailable(url);
      if (bot.isAvailable() != available) {
        bot.setAvailable(available);
        botService.updateBot(bot.getId(), bot);
        LOG.debug("Bot updated");
      }
    }


  }

  private boolean botAvailable(String url) {
    try {
      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.GET, null, String.class);
      return response.getStatusCode().value() > 400;
    } catch (HttpStatusCodeException exception) {
      // exception if the bot is up, but the method is wrong
      int statusCode = exception.getStatusCode().value();
      return statusCode > 400;
    } catch (Exception exception) {
      // exception if the bot is down
      return false;
    }
  }

}
