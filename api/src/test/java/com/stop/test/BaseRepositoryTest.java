package com.stop.test;

import com.stop.StopBackendApplication;
import com.stop.model.Bot;
import com.stop.model.BotAddress;
import com.stop.model.Branch;
import com.stop.model.User;
import com.stop.repository.BotAddressRepository;
import com.stop.repository.BotRepository;
import com.stop.repository.BranchRepository;
import com.stop.repository.ChatRepository;
import com.stop.repository.ChatRoomRepository;
import com.stop.repository.UserRepository;
import java.util.Date;
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
  protected BotRepository botRepository;

  @Autowired
  protected BotAddressRepository botAddressRepository;

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected BranchRepository branchRepository;

  @Autowired
  protected ChatRoomRepository chatRoomRepository;

  @Autowired
  protected ChatRepository chatRepository;

  @PostConstruct
  public void init() {
    User user = new User();
    user.setName("admin");
    user.setAdmin(true);
    user.setCreated(new Date());
    userRepository.save(user);
    
    Bot bot = new Bot();
    bot.setAvailable(true);
    bot.setCreated(new Date());
    bot.setName("Init bot");
    bot.setDescription("A bot on initial data");
    bot.setShowTo("ADMIN");
    botRepository.save(bot);
    
    BotAddress address=new BotAddress();
    address.setIp("localhost");
    address.setPort(5000);
    address.setApiPath("chatstop/bot_request");
    address.setBot(bot);
    botAddressRepository.save(address);
    
    Branch branch = new Branch();
    branch.setCreated(new Date());
    branch.setName("Init branch");
    branchRepository.save(branch);
  }

}
