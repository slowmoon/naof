package com.yipin.project.domain.payment;

import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.usertype.MapUserType;
import com.yipin.project.domain.application.Application;
import com.yipin.project.domain.order.Order;
import com.yipin.project.domain.product.Products;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;

@TypeDefs({@TypeDef(name = "mapJson", typeClass = MapUserType.class)})
@Entity
@Table(name = "tb_payment")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Payment extends BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addOrder(Order order){
        this.order = order;
    }

    public Payment(){}

    public Payment(Application application){
        this.application = application;
        this.products = application.getProducts();
    }

    @OneToOne
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",foreignKey = @ForeignKey(value=ConstraintMode.NO_CONSTRAINT))
    private Products products;     //产品信息

    private Long interactId;         //交互id信息

    @ManyToOne
    @JoinColumn(name = "appid",foreignKey = @ForeignKey(value=ConstraintMode.NO_CONSTRAINT))
    private Application application;    //应用id

    @Type(type = "mapJson")
    private Map<String, String> extInfo=null;  //扩展返回信息


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Long getInteractId() {
        return interactId;
    }

    public void setInteractId(Long interactId) {
        this.interactId = interactId;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public abstract void perform();
}
