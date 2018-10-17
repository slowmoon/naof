package com.yipin.project.service.product

import com.yipin.api.Types.JLong
import com.yipin.commons.utils.CodeStatus
import com.yipin.project.domain.product.Products
import com.yipin.project.rpt.product.ProductRpt
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

trait ProductService {

    def save(product: Products):Products

    def findById(id :JLong):Products

    def findByName(name:String):Products

}







@Service
class ProductServiceImpl extends ProductService{

 @Autowired val productRpt:ProductRpt = null


  @Transactional
  override def save(product: Products): Products = {
      productRpt.save(product)
  }

  override def findById(id: JLong): Products = {
       productRpt.findById(id).orElse(null)
  }

  override def findByName(name: String): Products = {
    val products = productRpt.findByCode(CodeStatus.ofName(name))
    if (products!=null && products.size()>0) return products.get(0)
    else null
  }
}
