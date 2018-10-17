package com.yipin.project.service.payment

import com.yipin.commons.entity.AcquireParams
import com.yipin.project.domain.order.Order
import com.yipin.project.rpt.application.ApplicationRpt
import com.yipin.project.rpt.order.OrderRpt
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

trait PaymentService {

    def createPayment(order:Order,param:Map[String,String]):Order
}


@Service
class PaymentServiceImpl extends PaymentService{

 @Autowired val applicationRpt:ApplicationRpt =null
 @Autowired val orderRpt:OrderRpt =null

  @Transactional
  override def createPayment(order:Order,param: Map[String, String]): Order = {
      val params = AcquireParams(param)
      val application = applicationRpt.findByAppid(params.appid)
      val payment = application.createPayment()
      order.addPayment(payment)
      orderRpt.save(order)
  }

}
