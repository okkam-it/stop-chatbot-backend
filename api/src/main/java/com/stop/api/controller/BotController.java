package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.BOT_SERVICE_BASE_PATH;

import com.stop.api.service.BotService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.BotDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(BOT_SERVICE_BASE_PATH)
public class BotController {

  @Autowired
  private BotService botService;

  @PostMapping
  @ResponseBody
  public ResponseEntity<BotDto> createBot(HttpServletRequest httpReq, @RequestBody BotDto req) {
    BotDto resp = botService.createBot(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  @PutMapping
  @ResponseBody
  public ResponseEntity<BotDto> updateBot(HttpServletRequest httpReq, @RequestBody BotDto req) {
    BotDto resp = botService.updateBot(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }


  // GET ALL
  @GetMapping
  @ResponseBody
  public List<BotDto> listBots() {
    return botService.listAllBots();
  }



}
