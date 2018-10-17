package com.yipin.prod;

import com.yipin.commons.utils.CodeStatus;
import com.yipin.commons.utils.ValueRegion;
import com.yipin.project.domain.application.Application;
import com.yipin.project.domain.application.ApplicationStatus;
import com.yipin.project.domain.contract.Contract;
import com.yipin.project.domain.contract.ContractStatus;
import com.yipin.project.domain.product.ProductStatus;
import com.yipin.project.domain.product.Products;
import com.yipin.project.domain.secret.SecretKey;
import com.yipin.project.rpt.SecretKeyRpt;
import com.yipin.project.rpt.contract.ContractRpt;
import com.yipin.project.service.acquire.AcquireService;
import com.yipin.project.service.application.ApplicationService;
import com.yipin.project.service.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DemoTest {

    @Autowired
    private ProductService service;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private AcquireService acquireService;

    @Autowired
    private SecretKeyRpt secretKeyRpt;

    @Autowired
    private ContractRpt contractRpt;


    @Test
    public void  testSave(){
        Products products = new Products();
        products.setCodeType(CodeStatus.ALICODE);
        products.setTimeRegions(new ValueRegion<Date>(new Date(), new Date()));
        products.setAmountRegions(new ValueRegion<>());
        products.setStatus(ProductStatus.VALID);
        products.setName("支付宝扫码");
        service.save(products);
    }

    @Test
    public void  testGet(){
        Products alijs = service.findByName("alijs");
        System.out.println(alijs);

        Products alicode = service.findByName("alicode");
        System.out.println(alicode);
    }

    @Test
    public void  test3(){

        Products alicode = service.findByName("alicode");

        Application application = new Application();
        application.setProducts(alicode);
        application.setStatus(ApplicationStatus.OPEN);
        application.setDateRegions(new ValueRegion<>(new Date(), new Date()));
        application.setContract(contractRpt.findById(3l).get());
        Application save = applicationService.save(application);
        System.out.println(save.getAppid());
    }



    @Test
    public void  test4() throws NoSuchAlgorithmException {

        Contract contract = new Contract();
        contract.setUserId(2l);
        SecretKey secretKey = new SecretKey();

        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(2048);
        KeyPair keyPair = rsa.generateKeyPair();
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        secretKey.setPriKey(privateKey);
        secretKey.setPubKey(publicKey);
        secretKey.setMd5Key("1234512345");
        contract.setSecretKey(secretKey);
        contract.setStatus(ContractStatus.OPEN);
        contractRpt.save(contract);
    }




}
