package com.yipin.project.rpt.order;
import com.yipin.project.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRpt extends JpaRepository<Order, Long> {

    Order queryOrderByTraceNoAndAppid(@Param("traceNo") String traceNo,@Param("appid") String appid);

}


