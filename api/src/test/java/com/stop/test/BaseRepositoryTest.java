package com.stop.test;

import com.stop.StopBackendApplication;
import com.stop.api.service.BotService;
import com.stop.api.service.BranchService;
import com.stop.api.service.ChatRoomService;
import com.stop.api.service.UserService;
import com.stop.api.utils.StopUtils;
import com.stop.dto.BotAddressDto;
import com.stop.dto.BotDto;
import com.stop.dto.BranchDto;
import com.stop.dto.UserDto;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {StopBackendApplication.class})
@Transactional
public abstract class BaseRepositoryTest {

  @Autowired
  protected BotService botService;

  @Autowired
  protected UserService userService;

  @Autowired
  protected BranchService branchService;

  @Autowired
  protected ChatRoomService chatRoomService;

  @PostConstruct
  public void init() {
    UserDto user = new UserDto();
    user.setUsername("admin");
    user.setAdmin(true);
    user.setUid(StopUtils.generateCode());
    userService.createUser(user);

    BotDto bot = new BotDto();
    bot.setAvailable(true);
    bot.setName("Init bot");
    bot.setDescription("A bot on initial data");
    bot.setShowTo("ADMIN");

    BotAddressDto address = new BotAddressDto();
    address.setIp("localhost");
    address.setPort(5000);
    address.setPath("chatstop/bot_request");
    bot.setAddress(address);
    botService.createBot(bot);

    BranchDto branch = new BranchDto();
    branch.setName("Init branch");
    branch.setCode(StopUtils.generateCode());
    branchService.createBranch(branch);
  }

}
