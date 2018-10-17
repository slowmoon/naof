package com.yipin.project

import javax.servlet.http.HttpServletRequest
import scala.collection.JavaConverters._
package object web {

    implicit def request2Map(request:HttpServletRequest):Map[String,String]={
      request.getParameterMap.asScala.map(entry=>(entry._1,entry._2(0))).toMap
    }

}
