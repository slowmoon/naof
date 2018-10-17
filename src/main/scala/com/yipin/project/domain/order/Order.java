package com.yipin.project.domain.order;

import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.AccessWay;
import com.yipin.commons.utils.DeliveryNotify;
import com.yipin.project.domain.application.Application;
import com.yipin.project.domain.contract.Contract;
import com.yipin.project.domain.payment.Payment;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "tb_order",indexes = {@Index(name = "index_appid_traceNO",columnList ="traceNo,appid"),@Index(name = "index_user_id",columnList = "userId"),@Index(name = "index_create_time",columnList = "createTime")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Order extends BaseObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order(){}

    public Order(Application application){
        this.appid = application.getAppid();
    }

    public Order(Contract contract){
        this.contract = contract;
    }

   /* @ManyToOne
    @JoinColumn(name = "appid", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Application application;
*/
    private DeliveryNotify deliveryNotify = new DeliveryNotify();

    @ManyToOne
    @JoinColumn(name = "contract_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Contract contract;

    private String returnUrl=null;

    private Long userId =null;

    private Date merchantTime=null;

    private Date finishTime = null;

    private BigDecimal amount =null;

    private BigDecimal succAmount=null;  //成功金额

    private BigDecimal cost =null;

    private String ip = null;

    private OrderStatus status= null;

    private SettleStatus settleStatus =null;

    private Date settleTime = null;      //结算时间

    private Integer accessWay = AccessWay.get();

    private String source;

    private String remark;

    private String description;

    private String traceNo;

    private String appid;


    private String code;

    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Payment payment =null;      //变成一对一关系


    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public BigDecimal getSuccAmount() {
        return succAmount;
    }

    public void setSuccAmount(BigDecimal succAmount) {
        this.succAmount = succAmount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public DeliveryNotify getDeliveryNotify() {
        return deliveryNotify;
    }

    public void setDeliveryNotify(DeliveryNotify deliveryNotify) {
        this.deliveryNotify = deliveryNotify;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Date getMerchantTime() {
        return merchantTime;
    }

    public void setMerchantTime(Date merchantTime) {
        this.merchantTime = merchantTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public SettleStatus getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(SettleStatus settleStatus) {
        this.settleStatus = settleStatus;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Integer getAccessWay() {
        return accessWay;
    }

    public void setAccessWay(Integer accessWay) {
        this.accessWay = accessWay;
    }
/*

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
*/

    public void perform(){
        this.status = OrderStatus.UNKNOW;
        this.payment.perform();
    }

    public void addPayment(Payment payment){
         payment.addOrder(this);
         this.setPayment(payment);
    }


    public Map<String,String> returnMap(){
        ResponseMap responseMap = new ResponseMap(this);
        return responseMap.toMap();
    }


    public static class ResponseMap{
        private String code;
        private String message;
        private String appid;
        private BigDecimal succAmount;
        private Map<String,String> extParams;
        private Date finishTime;
        private String traceNo;


        ResponseMap(Order order){
            this.code = order.getCode();
            this.message = order.getMessage();
            this.appid = order.getAppid();
            this.succAmount = order.succAmount;
            this.extParams = order.payment.getExtInfo();
            this.finishTime = order.finishTime;
            this.traceNo = order.traceNo;
        }


        public Map<String,String> toMap(){
            HashMap<String, String> maps = new HashMap<>();
            if (extParams!=null) maps.putAll(extParams);
            maps.put("code", this.getCode());
            maps.put("message", this.getMessage());
            maps.put("appid", this.getAppid());
            String amount = succAmount == null ? null : succAmount.setScale(2, RoundingMode.HALF_DOWN).toString();
            maps.put("succAmount", amount);
            String time = finishTime == null ? null : new SimpleDateFormat("yyyyMMddHHmmss").format(getFinishTime());
            maps.put("finishTime", time);
            maps.put("traceNo", traceNo);
            return maps;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public BigDecimal getSuccAmount() {
            return succAmount;
        }

        public void setSuccAmount(BigDecimal succAmount) {
            this.succAmount = succAmount;
        }

        public Map<String, String> getExtParams() {
            return extParams;
        }

        public void setExtParams(Map<String, String> extParams) {
            this.extParams = extParams;
        }

        public Date getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(Date finishTime) {
            this.finishTime = finishTime;
        }

        public String getTraceNo() {
            return traceNo;
        }

        public void setTraceNo(String traceNo) {
            this.traceNo = traceNo;
        }
    }

}
