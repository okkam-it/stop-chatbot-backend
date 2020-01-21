package com.stop.api.service;

import com.stop.dto.BotDto;
import com.stop.model.Bot;
import com.stop.model.BotAddress;
import com.stop.repository.BotAddressRepository;
import com.stop.repository.BotRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService {

  @Autowired
  private BotRepository botRepository;

  @Autowired
  private BotAddressRepository botAddressRepository;

  /**
   * Create a new bot.
   * 
   * @param req request
   * @return bot saved
   */
  public BotDto createBot(BotDto req) {
    Bot bot = new Bot();
    bot.setCreated(new Date());
    bot.setName(req.getName());
    bot.setDescription(req.getDescription());
    bot.setShowTo(req.getShowTo());

    BotAddress botAddress = new BotAddress();
    botAddress.setIp(req.getIp());
    botAddress.setPort(req.getPort());
    botAddress.setApiPath(req.getPath());
    Bot saved = botRepository.save(bot);
    saved.setBotAddress(botAddress);
    botAddress.setBot(saved);
    botAddressRepository.save(botAddress);
    return convertBotToDto(saved);
  }

  /**
   * Retrieve all configured bots.
   * 
   * @return all bots
   */
  public List<BotDto> listAllBots() {
    List<Bot> bots = botRepository.findAll();
    List<BotDto> botsResult = new ArrayList<>();
    for (Bot bot : bots) {
      botsResult.add(convertBotToDto(bot));
    }
    return botsResult;
  }

  private BotDto convertBotToDto(Bot bot) {
    BotDto botDto = new BotDto();
    botDto.setId(bot.getId());
    botDto.setName(bot.getName());
    botDto.setDescription(bot.getDescription());
    botDto.setIp(bot.getBotAddress().getIp());
    botDto.setPort(bot.getBotAddress().getPort());
    botDto.setPath(bot.getBotAddress().getApiPath());
    botDto.setShowTo(bot.getShowTo());
    return botDto;
  }

}
