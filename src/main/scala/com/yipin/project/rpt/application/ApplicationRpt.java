package com.yipin.project.rpt.application;

import com.yipin.project.domain.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRpt extends JpaRepository<Application, String> {

    Application findByAppid(String appid);

}
