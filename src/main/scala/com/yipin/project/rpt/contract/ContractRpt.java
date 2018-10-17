package com.yipin.project.rpt.contract;

import com.yipin.project.domain.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContractRpt  extends JpaRepository<Contract,Long>{

}
