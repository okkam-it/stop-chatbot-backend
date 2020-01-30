package com.stop.test;

import com.stop.model.Branch;
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
    user.setUsername("user");
    user.setAdmin(false);
    user.setCreated(new Date());
    User created = userRepository.save(user);
    Assert.assertTrue(created.getId() > 0);
  }

  // TODO fix
  public void findByName() {
    User user = userRepository.findOneByUsername("admin");
    Assert.assertNotNull(user);
  }

  @Test
  public void addBranchToUser() {
    User user = userRepository.findById(1L).get();
    Branch branch = branchRepository.findById(4L).get();
    user.getBranches().add(branch);
    User saved = userRepository.save(user);
    Assert.assertEquals(1, saved.getBranches().size());
  }
}
