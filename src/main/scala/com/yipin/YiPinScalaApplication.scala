package com.yipin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement(proxyTargetClass = true)
class YiPinScalaApplication {

}


object YiPinStarter extends App{

  SpringApplication.run(classOf[YiPinScalaApplication])

}
