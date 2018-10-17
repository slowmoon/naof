package com.yipin.project.service.order

import java.util.Date

import com.fangjs.commons.core.exception.BusinessException
import com.yipin.commons.entity.AcquireParams
import com.yipin.project.domain.application.Application
import com.yipin.project.domain.order.Order
import com.yipin.project.rpt.application.ApplicationRpt
import com.yipin.project.rpt.order.OrderRpt
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

trait OrderService {

  def createOrder(params:Map[String,String]):Order

}



@Service
class OrderServiceImpl extends OrderService{

  @Autowired val orderRpt:OrderRpt =null
  @Autowired val applicationRpt:ApplicationRpt =null


  @Transactional
  override def createOrder(params: Map[String, String]): Order = {
    val acquireParams = AcquireParams(params)
    val app = isValid(acquireParams)
    //TODO 检查应用id和流水号是否唯一
    val order = orderRpt.queryOrderByTraceNoAndAppid(acquireParams.traceNo, acquireParams.appid)
    if(order!=null) throw new BusinessException("com.yipin.order.005",acquireParams.traceNo)
    val contract = app.getContract
    val newOrder = contract.mkReceiptOrder()
    //TODO 检查签名验证是否OK
    newOrder.setAmount(acquireParams.amount)
    newOrder.setReturnUrl(acquireParams.returnUrl)
    newOrder.getDeliveryNotify.notifyUrl = acquireParams.notifyUrl
    newOrder.setIp(acquireParams.ip)
    newOrder.setRemark(acquireParams.remark)
    newOrder.setDescription(acquireParams.desc)
    newOrder.setMerchantTime(acquireParams.merchantTime)
    newOrder.setTraceNo(acquireParams.traceNo)
    newOrder.setUserId(contract.getUserId)
    newOrder.setAppid(acquireParams.appid)
    newOrder.setSource(acquireParams.source)
    orderRpt.save(newOrder)
  }


   @Transactional
   def isValid(params:AcquireParams):Application={
    val app = applicationRpt.findByAppid(params.appid)
    if (app==null) throw new BusinessException("com.yipin.application.001")            //验证一  应用是否存在
    if (!app.isValid) throw new BusinessException("com.yipin.application.002")          //验证二 应用是否有效
    val products = app.getProducts
    if (!products.amountApply(params.amount)) throw new BusinessException("com.yipin.application.003")  //应用金额是否支持
    if (!products.timeApply(new Date)) throw new BusinessException("com.yipin.application.004")   //应用时间是否支持
    app
  }

}