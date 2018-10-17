package com.yipin.commons.utils

import java.util.Date

import com.fangjs.commons.core.util.{StringUtils, ThrowableUtils}
import com.test.payagent.commons.exception.BusinessException
import javax.persistence.{Column, Embeddable, Transient}
import org.slf4j.LoggerFactory

/**
  * Created by ldc on 14-3-17.
  */

object DeliveryNotify {
  val NOTIFY_RESULT = "Y"
  //修改最大次数,为15次,不需要那么多通知.原来为30次
  val MAX_NOTIFY_COUNT = 15
  val NOTIFY_PERIOD = 60 * 1000l
}

@Embeddable
class DeliveryNotify extends Serializable {
  import DeliveryNotify._


  @Column(name = "notify_url")
  private[this] var notifyUrlPt: String = null
  @Column(name = "notify_result")
  private[this] var notifyResultPt: String = null
/*
  var notifyRequest: String = null
*/
  @Column(name = "notify_count")
  var notifyCount: Integer = 0
  @Column(name = "last_notify_time")
  var lastNotifyTime: Date = null
  @Column(name = "next_notify_time")
  var nextNotifyTime: Date = null

  @Transient
  var logger = LoggerFactory.getLogger(getClass)

  def this(notifyUrl: String) {
    this()
    this.notifyUrl = notifyUrl
  }

  def notifyResult = notifyResultPt

  def notifyResult_=(msg: String) {
    if (msg == null) return
    //解决返回通知的内容中有bom头的问题  0xFEFF
    var result:String=msg
    if (result.charAt(0)==0xFEFF) result=result.substring(1)
    notifyResultPt = if (result.length > 255) result.substring(0, 254) else result.trim
  }

  def notifyUrl = notifyUrlPt

  def notifyUrl_=(aNotifyUrl: String) {
    if (StringUtils.isBlank(aNotifyUrl)) throw new BusinessException("bc.agw.domain.ldc.36")
    notifyUrlPt = aNotifyUrl
  }

  def notify(params: String) {
    if (notifyUrl == null) {
      return
    }
    try {
      notifyResult = new HttpUtil(notifyUrl).send(params)
    } catch {
      case t: Throwable => {
        logger.error("执行通知[" + this + "]时发生异常", t)
        notifyResult = ThrowableUtils.getRootMessage(t)
      }
    }
    notifyCount += 1
    lastNotifyTime = new Date()
    nextNotifyTime = if (NOTIFY_RESULT.equals(notifyResult) || notifyCount > MAX_NOTIFY_COUNT) null
    else new Date(System.currentTimeMillis + notifyCount * NOTIFY_PERIOD)
  }

}

