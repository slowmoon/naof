package com.yipin.commons.utils

import com.yipin.project.domain.secret.SecretKey
import org.springframework.util.StringUtils

abstract class Protocol {

  def sortKeyValue(param:Map[String,String],secretKey: SecretKey):String

}

class ProtocolV11 extends Protocol{

  def excludes = Array("sign")

  override def sortKeyValue(param: Map[String, String],secretKey:SecretKey): String = {
     val sb = new StringBuilder()
     param.filterNot(p=> excludes.contains(p._1)).filterNot(p=>StringUtils.isEmpty(p._2)).
       toList.sortBy(_._1).foreach(p=>sb.append(p._1).append("=").append(p._2).append("&"))
      sb.append("key").append("=").append(secretKey.getMd5Key)
      secretKey.sign(sb.toString().getBytes())
  }
}


object Protocol {

  def apply(v:String): Protocol = v match {
    case "1.1" => new ProtocolV11()
    case _ => new ProtocolV11()
  }
}






