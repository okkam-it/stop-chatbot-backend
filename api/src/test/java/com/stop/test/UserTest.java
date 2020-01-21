package com.stop.test;

import com.stop.model.User;
import java.util.Date;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class UserTest extends BaseRepositoryTest {

  @Test
  public void getUserTest() {
    Optional<User> user = userRepository.findById(1L);
    Assert.assertNotNull(user);
  }

  @Test
  public void createUser() {
    User user = new User();
    user.setName("user");
    user.setAdmin(false);
    user.setCreated(new Date());
    User created = userRepository.save(user);
    Assert.assertTrue(created.getId() > 0);
  }
}
