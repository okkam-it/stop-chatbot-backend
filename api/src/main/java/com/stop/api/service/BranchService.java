package com.stop.api.service;

import com.stop.dto.BranchDto;
import com.stop.model.Branch;
import com.stop.repository.BranchRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

  @Autowired
  private BranchRepository branchRepository;

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
    return branchDto;
  }

}
