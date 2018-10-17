package com.yipin.project.domain.order;

import com.yipin.project.domain.application.Application;
import com.yipin.project.domain.contract.Contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class ReceiptOrder extends Order{

    public ReceiptOrder(){}

    public ReceiptOrder(Application contract){
        super(contract);
    }

    public ReceiptOrder(Contract contract){
        super(contract);
    }



}
