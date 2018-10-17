package com.yipin.commons.utils

import java.net.{HttpURLConnection, URL}
import java.security.cert.X509Certificate

import com.yipin.api.Types.JMap
import javax.net.ssl.{HttpsURLConnection, SSLContext, X509TrustManager}
import sun.net.www.protocol.https.DefaultHostnameVerifier

import scala.io.Source

/**
  * connection
  */


class HttpUtil(httpUrl:String) {

  private val httpConn :HttpURLConnection = init()

  def init():HttpURLConnection = {
      val url = new URL(httpUrl)
      var httpConnection :HttpURLConnection = null
      if (url.getProtocol.equals("http")){
         httpConnection = url.openConnection().asInstanceOf[HttpURLConnection]
      }else if (url.getProtocol.equals("https")){
         val httpsConnection = url.openConnection().asInstanceOf[HttpsURLConnection]
         val sslContext = SSLContext.getInstance("TLS")
         sslContext.init(null, Array(new DefaultTrustManager), null)
       val ssf =  sslContext.getSocketFactory
        httpsConnection.setHostnameVerifier(new DefaultHostnameVerifier)
        httpsConnection.setSSLSocketFactory(ssf)
      }
      httpConnection.setDoInput(true)
      httpConnection.setDoOutput(true)
      httpConnection.setUseCaches(false)
      httpConnection.setRequestMethod("POST")
/*
      httpConnection.setRequestProperty("Content-type", "application/x-www-urlencoded")
*/
      httpConnection.setConnectTimeout(10000)
      httpConnection.setReadTimeout(30000)
      httpConnection.connect()
      httpConnection
  }

  def send(msg:String):String = {
    val output = httpConn.getOutputStream
    output.write(msg.getBytes("UTF-8"))
    Source.fromInputStream(httpConn.getInputStream).mkString
  }

}


class DefaultTrustManager extends X509TrustManager{

  override def checkClientTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {}

  override def checkServerTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {}

  override def getAcceptedIssuers: Array[X509Certificate] = null
}


