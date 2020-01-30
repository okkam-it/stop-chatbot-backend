package com.stop.repository;

import com.stop.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {

  public Branch findOneByCode(String code);

}
