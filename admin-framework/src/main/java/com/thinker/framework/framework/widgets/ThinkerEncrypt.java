package com.thinker.framework.framework.widgets;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.DESede;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ThinkerEncrypt {

    /**
     * @title base64encode
     * @description
     * @author YangYuanCe
     * @param bytes
     * @return {@link String}
     **/
    public String base64encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * @title base64decode
     * @description 解密base64
     * @author YangYuanCe
     * @param s
     * @return {@link String}
     **/
    public byte[] base64decode(String s) {
        return Base64.decode(s);
    }

    /**
     * @title md5
     * @description 使用MD5进行加密
     * @author YangYuanCe
     * @param string
     * @return {@link String}
     **/
    public String md5(String string){
        return SecureUtil.md5(string).toUpperCase();
    }

    /**
     * @title sha1
     * @description 使用sha1进行加密
     * @author YangYuanCe
     * @param str
     * @return {@link String}
     **/
    public String sha1(String str) {
        return SecureUtil.sha1(str).toUpperCase();
    }

    /**
     * @title desEncrypt
     * @description 
     * @author YangYuanCe
     * @param data
     * @param key
     * @return {@link String}
     **/
    public String desEncrypt(String data, String key) {
        return (new DESede(Mode.CBC, Padding.PKCS5Padding, key.getBytes()))
                .setIv("01234567".getBytes(StandardCharsets.UTF_8))
                .encryptBase64(data);
    }

    /**
     * @title desDecrypt
     * @description des解密
     * @author YangYuanCe
     * @param data
     * @param key
     * @return {@link String}
     **/
    public String desDecrypt(String data, String key) {
        return (new DESede(Mode.CBC, Padding.PKCS5Padding, key.getBytes()))
                .setIv("01234567".getBytes(StandardCharsets.UTF_8))
                .decryptStr(data);
    }

    /**
     * @title createRsaPair
     * @description 随机生成密钥对
     * @author YangYuanCe
     * @return {@link Map< String, String>}
     **/
    public Map<String, String> createRsaPair() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        Map<String, String> map = new HashMap<>();
        map.put("private", base64encode(pair.getPrivate().getEncoded()));
        map.put("public", base64encode(pair.getPublic().getEncoded()));
        return map;
    }

    /**
     * @title rsaSign
     * @description rsa私钥签名
     * @author YangYuanCe
     * @param data
     * @param privateKeyStr
     * @return {@link String}
     **/
    public String rsaSign(String data, String privateKeyStr, SignAlgorithm signAlgorithm) {
        Sign sign = SecureUtil.sign(signAlgorithm, privateKeyStr, null);
        return base64encode(sign.sign(StrUtil.bytes(data)));
    }

    public String rsaMd5Sign(String data, String privateKeyStr) {
        return rsaSign(data, privateKeyStr, SignAlgorithm.MD5withRSA);
    }

    public String rsaSha1Sign(String data, String privateKeyStr) {
        return rsaSign(data, privateKeyStr, SignAlgorithm.SHA1withRSA);
    }

    public String rsaSha256Sign(String data, String privateKeyStr) {
        return rsaSign(data, privateKeyStr, SignAlgorithm.SHA256withRSA);
    }

    public String rsaSha512Sign(String data, String privateKeyStr) {
        return rsaSign(data, privateKeyStr, SignAlgorithm.SHA512withRSA);
    }

    public Boolean rsaVerfiy(String signStr, String data, String publicKeyStr, SignAlgorithm signAlgorithm) {
        Sign sign = SecureUtil.sign(signAlgorithm, null, publicKeyStr);
        return sign.verify(StrUtil.bytes(data), base64decode(signStr));
    }

    public Boolean rsaMd5Verfiy(String signStr, String data, String publicKeyStr) {
        return rsaVerfiy(signStr, data, publicKeyStr, SignAlgorithm.MD5withRSA);
    }

    public Boolean rsaSha1Verfiy(String signStr, String data, String publicKeyStr) {
        return rsaVerfiy(signStr, data, publicKeyStr, SignAlgorithm.SHA1withRSA);
    }

    public Boolean rsaSha256Verfiy(String signStr, String data, String publicKeyStr) {
        return rsaVerfiy(signStr, data, publicKeyStr, SignAlgorithm.SHA256withRSA);
    }

    public Boolean rsaSha512Verfiy(String signStr, String data, String publicKeyStr) {
        return rsaVerfiy(signStr, data, publicKeyStr, SignAlgorithm.SHA512withRSA);
    }

    /**
     * @title rsaEncrypt
     * @description Rsa公钥加密
     * @author YangYuanCe
     * @param data
     * @param publicKeyStr
     * @return {@link String}
     **/
    public String rsaEncrypt(String data, String publicKeyStr) {
        RSA rsa = SecureUtil.rsa(null, publicKeyStr);
        return rsa.encryptBase64(StrUtil.bytes(data), KeyType.PublicKey);
    }

    /**
     * @title rsaDecrypt
     * @description 使用rsa私钥进行解密
     * @author YangYuanCe
     * @param data
     * @param privateKeyStr
     * @return {@link String}
     **/
    public String rsaDecrypt(String data, String privateKeyStr) {
        RSA rsa = SecureUtil.rsa(privateKeyStr, null);
        return rsa.decryptStr(data, KeyType.PrivateKey);
    }
}
