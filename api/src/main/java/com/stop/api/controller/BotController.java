package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.BOT_SERVICE_BASE_PATH;

import com.stop.api.service.BotService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.BotDto;
import com.stop.response.GenericResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping(BOT_SERVICE_BASE_PATH)
public class BotController {

  @Autowired
  private BotService botService;

  /**
   * Retrieve a list of all bots configured.
   * 
   * @return a list of bots
   */
  @GetMapping
  @ResponseBody
  public List<BotDto> listBots() {
    return botService.listAllBots();
  }

  /**
   * Finds a bot given its id.
   * 
   * @param id bot id
   * @return a bot with the given id
   */
  @GetMapping("/find/byid/{id}")
  @ResponseBody
  public BotDto findById(@PathVariable Long id) {
    return botService.findById(id);
  }

  /**
   * Find a list of bots associated to a branch.
   * 
   * @param branchId branch id
   * @return all bots configured to the branch
   */
  @GetMapping("/find/bybranch/{branchId}")
  @ResponseBody
  public List<BotDto> findByBranch(@PathVariable Long branchId) {
    return botService.findByBranch(branchId);
  }

  /**
   * Create a new bot.
   * 
   * @param httpReq http request
   * @param req bot to create
   * @return bot created
   */
  @PostMapping
  @ResponseBody
  public ResponseEntity<BotDto> createBot(HttpServletRequest httpReq, @RequestBody BotDto req) {
    BotDto resp = botService.createBot(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  /**
   * Update a bot.
   * 
   * @param httpReq http request
   * @param id id of the bot to update
   * @param req bot to update
   * @return update response
   */
  @PutMapping("/{id}")
  @ResponseBody
  public BotDto updateBot(HttpServletRequest httpReq, @PathVariable Long id,
      @RequestBody BotDto req) {
    return botService.updateBot(id, req);
  }

  /**
   * Delete a bot.
   * 
   * @param id id of the bot to delete
   * @return delete response
   */
  @DeleteMapping("/{id}")
  @ResponseBody
  public GenericResponse deleteBot(@PathVariable Long id) {
    return botService.delete(id);
  }

}
