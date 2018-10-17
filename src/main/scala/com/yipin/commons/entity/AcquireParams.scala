package com.yipin.commons.entity

import com.test.payagent.commons.date.DateUtil.RichString
import com.yipin.api.Types.JBigDecimal

case class AcquireParams(params:Map[String,String]) {

    def appid = params("appid")

    def sign = params("sign")

    def amount = new JBigDecimal(params("amount"))

    def traceNo = params("traceNo")

    def merchantTime = params("merchantTime").yyyyMMddHHmmss

    def notifyUrl = params("notifyUrl")

    def returnUrl = params.getOrElse("returnUrl", null)

    def remark = params.getOrElse("remark",null)

    def ip = params.getOrElse("ip", "127.0.0.1").split(",")(0)

    def desc  = params("desc")

    def source = params.getOrElse("source", "pc").toLowerCase

    def version = params.getOrElse("version", "1.1")

}
