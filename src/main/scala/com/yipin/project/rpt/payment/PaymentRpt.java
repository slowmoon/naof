package com.yipin.project.rpt.payment;

import com.yipin.project.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRpt extends JpaRepository<Payment,Long> {

}
