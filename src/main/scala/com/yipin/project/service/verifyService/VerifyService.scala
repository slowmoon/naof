package com.yipin.project.service.verifyService

import com.alibaba.druid.support.json.JSONUtils
import com.test.payagent.commons.exception.BusinessException
import com.yipin.api.Types.JMap
import com.yipin.commons.entity.AcquireParams
import com.yipin.commons.utils.Protocol
import com.yipin.project.domain.secret.SecretKey
import com.yipin.project.rpt.application.ApplicationRpt
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import scala.collection.JavaConverters._
trait VerifyService {

  def verify(param:Map[String,String]):Boolean

  def sign(param:Map[String,String], secretKey: SecretKey):String

  def encrypt(param:Map[String,String], secretKey: SecretKey):String

  def decrypt(param:String,secretKey: SecretKey):JMap[String,String]

}

@Service
class VerifyServiceImpl extends VerifyService{

  @Autowired
  val applicationRpt:ApplicationRpt =null


  @Transactional
  override def verify(param: Map[String, String]): Boolean = {
     val ac = AcquireParams(param)
     val ap =applicationRpt.findByAppid(ac.appid)
     val key = ap.getContract.getSecretKey
     if(key==null) throw new BusinessException("com.yipin.application.006")
     if(!sign(param, key).equals(ac.sign))return true else false
  }

  override def sign(param: Map[String, String], secretKey: SecretKey): String = {
    val ac = AcquireParams(param)
    Protocol(ac.version).sortKeyValue(param, secretKey)
  }

  override def encrypt(param: Map[String, String], secretKey: SecretKey): String = {
     val jsonString =  JSONUtils.toJSONString(param.asJava)
      secretKey.encrypt(jsonString.getBytes())
  }

  override def decrypt(param: String, secretKey: SecretKey): JMap[String, String] = {
      val decryptedString = secretKey.decrypt(param.getBytes())
       JSONUtils.parse(decryptedString).asInstanceOf[JMap[String,String]]
  }

}

