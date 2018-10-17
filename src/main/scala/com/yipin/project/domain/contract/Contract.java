package com.yipin.project.domain.contract;

import com.fangjs.commons.core.exception.BusinessException;
import com.fangjs.commons.core.valueobject.SupportElements;
import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.usertype.SupportElementType;
import com.yipin.commons.utils.usertype.UserCodeType;
import com.yipin.project.domain.application.Application;
import com.yipin.project.domain.order.ReceiptOrder;
import com.yipin.project.domain.secret.SecretKey;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import javax.persistence.*;
import java.util.List;


@TypeDefs({@TypeDef(name = "supportElement", typeClass = SupportElementType.class),
        @TypeDef(name = "contractStatus", typeClass = UserCodeType.class,parameters = {@org.hibernate.annotations.Parameter(name="class", value = "com.yipin.project.domain.contract.ContractStatus")})
})
@Entity
@Table(name = "tb_contract")
public class Contract extends BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReceiptOrder mkReceiptOrder(){
        if (status.canCreateOrder()) {
            return new ReceiptOrder(this);
        }
        throw new BusinessException("com.yipin.contract.001");
    }

    @Type(type = "contractStatus")
    private ContractStatus status;

    private Long userId;

    private Boolean permitRefund = true;

    @OneToOne(cascade = CascadeType.ALL)
    private SecretKey secretKey=null;

    @Type(type = "supportElement")
    private SupportElements hasAuths =null;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="contract" )
    private List<Application>  apps =null;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getPermitRefund() {
        return permitRefund;
    }

    public void setPermitRefund(Boolean permitRefund) {
        this.permitRefund = permitRefund;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public SupportElements getHasAuths() {
        return hasAuths;
    }

    public void setHasAuths(SupportElements hasAuths) {
        this.hasAuths = hasAuths;
    }

    public List<Application> getApps() {
        return apps;
    }

    public void setApps(List<Application> apps) {
        this.apps = apps;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
