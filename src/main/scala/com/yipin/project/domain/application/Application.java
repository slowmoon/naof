package com.yipin.project.domain.application;


import com.fangjs.commons.core.exception.BusinessException;
import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.ValueRegion;
import com.yipin.commons.utils.usertype.UserCodeType;
import com.yipin.project.domain.contract.Contract;
import com.yipin.project.domain.order.ReceiptOrder;
import com.yipin.project.domain.payment.MobilePayment;
import com.yipin.project.domain.payment.Payment;
import com.yipin.project.domain.product.Products;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@TypeDefs({
        @TypeDef(name = "applicationStatus", typeClass = UserCodeType.class,parameters = {@org.hibernate.annotations.Parameter(name="class", value = "com.yipin.project.domain.application.ApplicationStatus")})
})
@Entity
@Table(name = "tb_application" )
public class Application extends BaseObject {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.yipin.commons.hibernate.IdGenerator")
    private String appid;

    @ManyToOne
    @JoinColumn(name = "contract_id",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    private BigDecimal feeAmount;

    private BigDecimal balance;   //余额

    @Type(type = "applicationStatus")
    private ApplicationStatus status;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "begin",column = @Column(name = "begin_time")), @AttributeOverride(name = "end",column = @Column(name = "end_time"))})
    private ValueRegion<Date> dateRegions;

    public Application(){}

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ValueRegion<Date> getDateRegions() {
        return dateRegions;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }


    public void setDateRegions(ValueRegion<Date> dateRegions) {
        this.dateRegions = dateRegions;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }


    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ReceiptOrder newOrder(){
        if (status.canCreateOrder()) {
            return new ReceiptOrder(this);
        }else {
            throw new BusinessException("com.yipin.application.001");
        }
    }


    public Payment createPayment(){
        return new MobilePayment(this);
    }

    public boolean isValid(){

        return status.canCreateOrder() && dateRegions.apply(new Date());
    }



}
