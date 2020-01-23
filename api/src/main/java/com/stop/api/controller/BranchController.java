package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.BRANCH_SERVICE_BASE_PATH;

import com.stop.api.service.BranchService;
import com.stop.dto.BranchDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(BRANCH_SERVICE_BASE_PATH)
public class BranchController {

  @Autowired
  private BranchService branchService;

  // GET ALL
  @GetMapping
  @ResponseBody
  public List<BranchDto> listBranches() {
    return branchService.listAllBranches();
  }
}
