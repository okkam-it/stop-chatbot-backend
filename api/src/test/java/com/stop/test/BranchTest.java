package com.stop.test;

import com.stop.model.Bot;
import com.stop.model.Branch;
import java.util.Date;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class BranchTest extends BaseRepositoryTest {

  @Test
  public void createBranch() {
    Branch branch = new Branch();
    branch.setCreated(new Date());
    branch.setName("Test branch");
    Branch saved = branchRepository.save(branch);
    Assert.assertTrue(saved.getId() > 0);
  }

  @Test
  public void addBotToBranch() {
    Bot bot = botRepository.findById(2L).get();
    Branch branch = branchRepository.findById(4L).get();
    branch.getBots().add(bot);
    Branch botAdded = branchRepository.save(branch);
    Assert.assertEquals(1, botAdded.getBots().size());
  }

  @Test(expected = NoSuchElementException.class)
  public void deleteBranch() {
    Branch branch = new Branch();
    branch.setCreated(new Date());
    branch.setName("Branch to delete");
    Branch saved = branchRepository.save(branch);
    Assert.assertTrue(saved.getId() > 0);
    branchRepository.deleteById(saved.getId());
    branchRepository.findById(branch.getId()).get();
  }

}
