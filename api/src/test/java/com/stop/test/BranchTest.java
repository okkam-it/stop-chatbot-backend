package com.stop.test;

import com.stop.dto.BotDto;
import com.stop.dto.BranchDto;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;

public class BranchTest extends BaseRepositoryTest {

  @Test
  public void getBranches() {
    List<BranchDto> branches = branchService.listAllBranches();
    Assert.assertTrue(branches.size() > 0);
  }

  @Test
  public void createBranchDto() {
    BranchDto branch = new BranchDto();
    branch.setName("Test branch");
    BranchDto saved = branchService.createBranch(branch);
    Assert.assertTrue(saved.getId() > 0);
  }

  @Test
  public void addBotToBranchDto() {
    BotDto bot = botService.findById(2L);
    BranchDto branch = branchService.findById(4L);
    branch.getBots().add(bot.getId());
    BranchDto botAdded = branchService.updateBranch(branch.getId(), branch);
    Assert.assertEquals(1, botAdded.getBots().size());
  }

  @Test(expected = ResponseStatusException.class)
  public void deleteBranchDto() {
    BranchDto branch = new BranchDto();
    branch.setName("BranchDto to delete");
    BranchDto saved = branchService.createBranch(branch);
    Assert.assertTrue(saved.getId() > 0);
    branchService.delete(saved.getId());
    branchService.findById(saved.getId());
  }

}
