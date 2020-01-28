package com.stop.api.service;

import com.stop.api.utils.StopUtils;
import com.stop.dto.BranchDto;
import com.stop.model.Bot;
import com.stop.model.Branch;
import com.stop.model.User;
import com.stop.repository.BotRepository;
import com.stop.repository.BranchRepository;
import com.stop.repository.UserRepository;
import com.stop.response.GenericResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

  @Autowired
  private BranchRepository branchRepository;

  @Autowired
  private BotRepository botRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Retrieve all branches.
   * 
   * @return a list with all branches created
   */
  public List<BranchDto> listAllBranches() {
    List<Branch> branches = branchRepository.findAll();
    List<BranchDto> result = new ArrayList<>();
    for (Branch branch : branches) {
      result.add(convertBranchToDto(branch));
    }
    return result;
  }

  private BranchDto convertBranchToDto(Branch branch) {
    BranchDto branchDto = new BranchDto();
    branchDto.setId(branch.getId());
    branchDto.setCode(branch.getCode());
    branchDto.setName(branch.getName());
    List<Long> bots = new ArrayList<>();
    for (Bot bot : branch.getBots()) {
      bots.add(bot.getId());
    }
    branchDto.setBots(bots);
    return branchDto;
  }

  /**
   * Create a new branch.
   * 
   * @param req branch to create
   * @return the branch created
   */
  public BranchDto createBranch(BranchDto req) {
    Branch branch = new Branch();
    branch.setCreated(new Date());
    branch.setCode(StopUtils.generateCode());
    branch.setName(req.getName());
    updateBranchBots(branch, req);
    Branch saved = branchRepository.save(branch);
    return convertBranchToDto(saved);
  }

  private void updateBranchBots(Branch branch, BranchDto req) {
    Set<Bot> bots = new HashSet<>();
    if (req.getBots() != null) {
      for (Long botId : req.getBots()) {
        bots.add(botRepository.findById(botId).get());
      }
    }
    branch.setBots(bots);

  }

  /**
   * Update the branch.
   * 
   * @param id id of the branch to update
   * @param req update request
   * @return OK if the branch was updated successfully
   */
  public GenericResponse updateBranch(Long id, BranchDto req) {
    GenericResponse response = new GenericResponse();
    try {
      Branch branch = branchRepository.findById(id).get();
      branch.setName(req.getName());
      updateBranchBots(branch, req);
      branchRepository.save(branch);
      response.setMessage("OK");
    } catch (NoSuchElementException e) {
      response.setMessage("KO");
    }
    return response;
  }

  /**
   * Delete branch by id.
   * 
   * @param id branch id to delete
   * @return OK if branch deleted successfully
   */
  public GenericResponse delete(Long id) {
    GenericResponse response = new GenericResponse();
    Branch branch = branchRepository.findById(id).get();
    if (branch == null) {
      response.setMessage("KO");
      return response;
    }
    branchRepository.deleteById(id);
    response.setMessage("OK");
    return response;
  }

  /**
   * Find branches by user.
   * 
   * @param userId user id
   * @return all branches associated to the user
   */
  public List<BranchDto> findByUser(Long userId) {
    User user = userRepository.findById(userId).get();
    Set<Branch> branches = user.getBranches();
    List<BranchDto> result = new ArrayList<>();
    for (Branch branch : branches) {
      result.add(convertBranchToDto(branch));
    }
    return result;
  }

}
