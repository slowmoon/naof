package com.yipin.project.domain.product;

import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.CodeStatus;
import com.yipin.commons.utils.usertype.UserCodeType;
import com.yipin.commons.utils.ValueRegion;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@TypeDefs(
        {
            @TypeDef(name = "prodCode", typeClass = UserCodeType.class,parameters = {@org.hibernate.annotations.Parameter(name="class", value = "com.yipin.commons.utils.CodeStatus")}),
            @TypeDef(name = "prodStatus", typeClass = UserCodeType.class,parameters = {@org.hibernate.annotations.Parameter(name="class", value = "com.yipin.project.domain.product.ProductStatus")})

        })
@Access(AccessType.FIELD)
@Entity
@Table(name = "tb_product")
public class Products extends BaseObject {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    @Type(type = "prodStatus")
    private ProductStatus status;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "begin", column = @Column(name = "begin_time")),@AttributeOverride(name = "end", column = @Column(name = "end_time"))})
    private ValueRegion<Date> timeRegions;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "begin", column = @Column(name = "floor_amount")),@AttributeOverride(name = "end", column = @Column(name = "ceil_amount"))})
    private ValueRegion<BigDecimal> amountRegions;

    @Type(type = "prodCode")
    @Column(unique = true)
    private CodeStatus codeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValueRegion<Date> getTimeRegions() {
        return timeRegions;
    }

    public void setTimeRegions(ValueRegion<Date> timeRegions) {
        this.timeRegions = timeRegions;
    }

    public ValueRegion<BigDecimal> getAmountRegions() {
        return amountRegions;
    }

    public void setAmountRegions(ValueRegion<BigDecimal> amountRegions) {
        this.amountRegions = amountRegions;
    }

    public CodeStatus getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeStatus codeType) {
        this.codeType = codeType;
    }

    public boolean timeApply(Date date){
        if (timeRegions==null)return true;
        return timeRegions.apply(date);
    }

    public boolean amountApply(BigDecimal decimal){
        if (amountRegions==null) return true;
        return amountRegions.apply(decimal);
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
