package com.yipin.project.rpt.product;

import com.yipin.commons.utils.CodeStatus;
import com.yipin.project.domain.product.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRpt extends JpaRepository<Products,Long> {

     @Query("from Products where codeType =:code")
     List<Products> findByCode(@Param("code") CodeStatus codeType);
}
