package com.yipin.project.service.acquire

import com.yipin.project.domain.order.Order
import com.yipin.project.service.order.OrderService
import com.yipin.project.service.payment.PaymentService
import javax.annotation.Resource
import org.springframework.stereotype.Service

trait AcquireService {
    def createOrderAndPayment(params:Map[String,String]):Order

}



@Service
class AcquireServiceImpl extends AcquireService{
  @Resource val orderService:OrderService =null
  @Resource val paymentService:PaymentService =null


  override def createOrderAndPayment(params: Map[String, String]): Order = {
    val order = orderService.createOrder(params)
    val newOrder = paymentService.createPayment(order, params)
    newOrder
  }
}
