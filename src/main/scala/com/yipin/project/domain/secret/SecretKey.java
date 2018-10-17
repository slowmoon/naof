package com.yipin.project.domain.secret;

import com.yipin.commons.entity.BaseObject;
import com.yipin.commons.utils.CipherUtils;
import com.yipin.commons.utils.ValueRegion;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_secret_key")
public class SecretKey extends BaseObject {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Lob
    @Column(columnDefinition = "TEXT")
    private String pubKey;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String priKey;

    private String md5Key;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "begin",column = @Column(name = "begin_time")), @AttributeOverride(name = "end",column = @Column(name = "end_time"))})
    private ValueRegion<Date> dateRegions;

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public ValueRegion<Date> getDateRegions() {
        return dateRegions;
    }

    public void setDateRegions(ValueRegion<Date> dateRegions) {
        this.dateRegions = dateRegions;
    }


    public String sign(byte[] message){
        return CipherUtils.getInstance().md5Sign(message);
    }

    public String encrypt(byte[] message) throws Exception {
        return CipherUtils.getInstance().encrypt(message,getPubKey());
    }

    public String decrypt(byte[] message) throws Exception {
        return CipherUtils.getInstance().decrypt(message, getPriKey());
    }

}
