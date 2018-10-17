package com.yipin.project.service.application

import com.yipin.project.domain.application.Application
import com.yipin.project.rpt.application.ApplicationRpt
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

trait ApplicationService {

  def save(app:Application):Application
}


@Service
class ApplicationServiceImpl extends ApplicationService{

 @Autowired val applicationRpt:ApplicationRpt =null

  @Transactional
  override def save(app: Application): Application = {
     applicationRpt.save(app)
  }
}
