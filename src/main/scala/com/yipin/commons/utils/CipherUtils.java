package com.yipin.commons.utils;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CipherUtils {

    private static final String MD5 = "md5";
    private static final String SHA256 = "sha256";

    private static final String RSA = "RSA/ECB/PKCS1Padding";

    private static final String keyAlgorithm = "RSA";


    private CipherUtils(){}

    private static  CipherUtils INSTANCE;

    public static CipherUtils getInstance(){
        if (INSTANCE==null){
            synchronized (CipherUtils.class){
                if (INSTANCE==null){
                    INSTANCE = new CipherUtils();
                }
            }
        }
        return INSTANCE;
    }

    public String md5Sign(byte[] bytes){
        try {
            MessageDigest instance = MessageDigest.getInstance(MD5);
            instance.update(bytes);
            return bytes2Hex(instance.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String bytes2Hex(byte[] message){
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<message.length;i++){
            String s = Integer.toHexString(message[i]&0xff);
            if (s.length()==1){
                sb.append(0);
            }
            sb.append(s);
        }
        return sb.toString();
    }


    public PublicKey getPublicKey(String key){
        try {
            byte[] decode = Base64.getDecoder().decode(key.getBytes("UTF-8"));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
            return KeyFactory.getInstance(keyAlgorithm).generatePublic(x509EncodedKeySpec);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PrivateKey getPrivateKey(String key){
        try {
            byte[] decode = Base64.getDecoder().decode(key.getBytes("UTF-8"));
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
           return KeyFactory.getInstance(keyAlgorithm).generatePrivate(pkcs8EncodedKeySpec);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encrypt(byte[] message,String key) throws Exception{
        try {
            Cipher instance = Cipher.getInstance(RSA);
            instance.init(Cipher.ENCRYPT_MODE,getPublicKey(key));
            instance.update(message);
            return Base64.getEncoder().encodeToString(instance.doFinal());
        } catch (Exception e) {
            throw e;
        }
    }

    public String decrypt(byte[] message,String key)throws Exception{
        try {
            Cipher instance = Cipher.getInstance(RSA);
            instance.init(Cipher.DECRYPT_MODE,getPrivateKey(key));
            instance.update(message);
            return new String(instance.doFinal());
        } catch (Exception e) {
            throw e;
        }
    }

}
