package com.yipin.project.domain.payment;



import com.yipin.project.domain.application.Application;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "1")
public class MobilePayment extends Payment{

    public MobilePayment(){}

    public MobilePayment(Application application){
        super(application);
    }

    @Override
    public void perform() {

    }





}
