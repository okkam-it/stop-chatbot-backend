package com.stop.api.service;

import com.stop.dto.BotAddressDto;
import com.stop.dto.BotDto;
import com.stop.dto.ChatDto;
import com.stop.dto.response.BotResponseDto;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BotService {

  @Autowired
  private BotRepository botRepository;

  @Autowired
  private BotAddressRepository botAddressRepository;

  @Autowired
  private BranchRepository branchRepository;

  @Autowired
  private RestTemplate restTemplate;

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
   * @return bot updated
   */
  public BotDto updateBot(Long id, BotDto req) {
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
      Bot updated = botRepository.save(bot);
      return convertBotToDto(updated);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found.");
    }
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found.");
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found.", e);
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
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found.", e);
    }
  }

  /**
   * Send a message to a bot.
   * 
   * @param botId bot to send
   * @param message message to send
   * @return the response from the bot
   */
  public ChatDto sendMessageToBot(Long botId, ChatDto message) {
    Bot bot = botRepository.findById(botId).get();
    if (bot == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bot not found.");
    }
    BotAddress address = bot.getBotAddress();
    String url =
        String.format("http://%s:%s/%s/", address.getIp(), address.getPort(), address.getApiPath());
    HttpEntity<?> entity = new HttpEntity<>(message);
    ResponseEntity<BotResponseDto[]> response =
        restTemplate.exchange(url, HttpMethod.POST, entity, BotResponseDto[].class);
    BotResponseDto[] responsesDto = response.getBody();
    if (responsesDto.length > 0) {
      ChatDto chatDto = new ChatDto();
      chatDto.setChatRoomId(message.getChatRoomId());
      chatDto.setMessage(responsesDto[0].getText());
      chatDto.setType("bot");
      chatDto.setUser("bot");
      chatDto.setSendDate(new Date());
      return chatDto;
    }
    return null;
    // TODO manage error
  }

}
