package com.stop.test;

import com.stop.dto.BranchDto;
import com.stop.dto.UserBioDataDto;
import com.stop.dto.UserDto;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class UserTest extends BaseRepositoryTest {

  @Test
  public void getUsers() {
    List<UserDto> users = userService.listAllUsers();
    Assert.assertTrue(users.size() > 0);
  }

  @Test
  public void getUserByIdTest() {
    UserDto user = userService.findById(1L);
    Assert.assertNotNull(user);
  }

  @Test
  public void getUserByUidTest() {
    UserDto user = userService.findById(1L);
    Assert.assertNotNull(user);
    UserDto uidUser = userService.findByUid(user.getUid());
    Assert.assertNotNull(uidUser);
  }

  @Test
  public void getUserByNameTest() {
    UserDto user = userService.findByName("admin");
    Assert.assertNotNull(user);
  }

  @Test
  public void createUser() {
    UserDto user = new UserDto();
    user.setUsername("user");
    user.setAdmin(false);
    UserDto created = userService.createUser(user);
    Assert.assertTrue(created.getId() > 0);
  }

  @Test
  public void createUserBioData() {
    UserBioDataDto bioData = new UserBioDataDto();
    bioData.setHeartRate(60);
    userService.saveUserBioData(1L, bioData);
    List<UserBioDataDto> datas = userService.findAllUserBioData(1L);
    Assert.assertEquals(1, datas.size());
  }

  @Test
  public void addBranchToUser() {
    UserDto user = userService.findById(1L);
    BranchDto branch = branchService.findById(4L);
    userService.addBranchToUser(user.getUid(), branch.getCode());
    List<BranchDto> branches = branchService.findByUser(user.getId());
    Assert.assertEquals(1, branches.size());
  }
}
