package com.yipin.project.web

import com.fangjs.commons.core.exception.BusinessException
import com.fangjs.commons.core.util.BusinessExceptionUtils
import com.yipin.commons.utils.AccessWay
import com.yipin.project.domain.order.Order
import com.yipin.project.service.acquire.AcquireService
import com.yipin.project.service.verifyService.VerifyService
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
@RequestMapping(Array("/receipt"))
class AcquireController{

    @RequestMapping(Array("/payment"))
    def mkReceiptOrder(implicit request:HttpServletRequest):Map[String,String]={
      template(param =>acquireService.createOrderAndPayment(param))
    }

    @RequestMapping(Array("/query"))
    def mkQueryOrder(implicit request:HttpServletRequest):Map[String,String]={
      null
    }

    @RequestMapping(Array("/refund"))
    def mkRefundOrder(implicit request:HttpServletRequest):Map[String,String]={
      null
    }


    def template(f: Map[String,String]=>Order)
                (implicit request: HttpServletRequest):Map[String,String]={

      val receiptMap = request2Map(request)
      AccessWay.set(AccessWay.Way.API.getWay)
      val result= try {
        logger.info(s"receive order [$receiptMap]")
        if (!verifyService.verify(receiptMap)) {
           logger.error(s"receipt order [$receiptMap] verify sign error!")
          throw new BusinessException("")
        }
         f(receiptMap)
      }catch {
        case e:BusinessException=>  e.printStackTrace()

          logger.error(s"receive order [$receiptMap] got business error [${e.getMessage}]")
            val codeMessage = BusinessExceptionUtils.getBusinessInfo(e.getMessage).split("|",2)
            Map("code"->codeMessage(0) , "message"-> codeMessage(1))
        case e:Exception =>   e.printStackTrace()
          logger.error(s"receive order [$receiptMap] got system error [${e.getMessage}]")
           Map("code"->"999", "message"->"系统异常")
      }
       null
    }


  val logger = LoggerFactory.getLogger(classOf[AcquireController])

  @Resource val acquireService:AcquireService =null
  @Resource val verifyService:VerifyService =null

}
