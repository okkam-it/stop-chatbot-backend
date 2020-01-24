package com.stop.api.controller;

import static com.stop.api.utils.StopConstants.BRANCH_SERVICE_BASE_PATH;

import com.stop.api.service.BranchService;
import com.stop.api.utils.ResponseUtils;
import com.stop.dto.BranchDto;
import com.stop.response.GenericResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
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

  @PostMapping
  @ResponseBody
  public ResponseEntity<BranchDto> createBranch(HttpServletRequest httpReq,
      @RequestBody BranchDto req) {
    BranchDto resp = branchService.createBranch(req);
    return ResponseEntity.created(ResponseUtils.getCreationUri(httpReq, resp.getId())).body(resp);
  }

  @PutMapping("/{id}")
  @ResponseBody
  public GenericResponse updateBranch(HttpServletRequest httpReq, @PathVariable Long id,
      @RequestBody BranchDto req) {
    return branchService.updateBranch(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public GenericResponse deleteBranch(@PathVariable Long id) {
    return branchService.delete(id);
  }
}
