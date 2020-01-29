package com.stop.api.service;

import com.stop.dto.BotAddressDto;
import com.stop.dto.BotDto;
import com.stop.model.Bot;
import com.stop.model.BotAddress;
import com.stop.model.Branch;
import com.stop.repository.BotAddressRepository;
import com.stop.repository.BotRepository;
import com.stop.repository.BranchRepository;
import com.stop.response.GenericResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService {

  @Autowired
  private BotRepository botRepository;

  @Autowired
  private BotAddressRepository botAddressRepository;

  @Autowired
  private BranchRepository branchRepository;

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
    botAddress.setIp(req.getAddress().getIp());
    botAddress.setPort(req.getAddress().getPort());
    botAddress.setApiPath(req.getAddress().getPath());
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
    BotAddressDto botAddressDto = new BotAddressDto();
    botAddressDto.setIp(bot.getBotAddress().getIp());
    botAddressDto.setPort(bot.getBotAddress().getPort());
    botAddressDto.setPath(bot.getBotAddress().getApiPath());
    botDto.setAddress(botAddressDto);
    botDto.setShowTo(bot.getShowTo());
    botDto.setAvailable(bot.isAvailable());
    return botDto;
  }

  /**
   * Update an existing bot.
   * 
   * @param req bot to update
   * @return
   */
  public GenericResponse updateBot(Long id, BotDto req) {
    GenericResponse response = new GenericResponse();
    try {
      Bot bot = botRepository.findById(id).get();
      bot.setName(req.getName());
      bot.setDescription(req.getDescription());
      bot.setShowTo(req.getShowTo());
      bot.setAvailable(req.isAvailable());
      bot.getBotAddress().setIp(req.getAddress().getIp());
      bot.getBotAddress().setPort(req.getAddress().getPort());
      bot.getBotAddress().setApiPath(req.getAddress().getPath());
      botAddressRepository.save(bot.getBotAddress());
      botRepository.save(bot);
      response.setMessage("OK");
    } catch (NoSuchElementException e) {
      response.setMessage("KO");
    }
    return response;
  }

  /**
   * Delete a bot.
   * 
   * @param id bot id to delete
   * @return OK if bot was successfully deleted
   */
  @Transactional
  public GenericResponse delete(Long id) {
    GenericResponse response = new GenericResponse();
    Bot bot = botRepository.findById(id).get();
    if (bot == null) {
      response.setMessage("KO");
      return response;
    }
    botAddressRepository.deleteByBot(bot);
    botRepository.deleteById(id);
    response.setMessage("OK");
    return response;
  }

  /**
   * Find bot by id.
   * 
   * @param id bot id
   * @return bot
   */
  public BotDto findById(Long id) {
    try {
      Bot bot = botRepository.findById(id).get();
      return convertBotToDto(bot);
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  /**
   * Find all bots in a branch.
   * 
   * @param branchId branch id
   * @return bots in that branch
   */
  public List<BotDto> findByBranch(Long branchId) {
    try {
      Branch branch = branchRepository.findById(branchId).get();
      Set<Bot> bots = branch.getBots();
      List<BotDto> response = new ArrayList<>();
      for (Bot bot : bots) {
        response.add(convertBotToDto(bot));
      }
      return response;
    } catch (NoSuchElementException e) {
      return null;
    }
  }

}
