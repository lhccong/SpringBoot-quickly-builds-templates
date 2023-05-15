package com.cong.springbootinit.config;


import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PrivateKey;

@Component
@ConfigurationProperties(prefix = "wx-config")
@Data
@Slf4j
public class WxConfig {

    /**
     商户号
     *
     */
    private String mchId;
    /**
     商户API证书序列号
     *
     */
    private String mchSerialNo;

    /**
     * 商户私钥文件
     */
    private String privateKeyPath;

    /**
     * APIv3密钥
      */
    private String apiV3Key;

    /**
     *  APPID
     */
    private String appid;

    /**
     * 微信服务器地址
     */
    private String domain;

    /**
     * 接收结果通知地址
     */
    private String notifyDomain;

    /**
     * APIv2密钥
     */
    private String partnerKey;

    /**
     * 获取商户的私钥文件
     * @param filename
     * @return
     */
    private PrivateKey getPrivateKey(String filename){

        try {
            return PemUtil.loadPrivateKey(new FileInputStream("D:\\develop\\KbBacknd-cloud\\KbBacknd\\apiclient_key.pem"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("私钥文件不存在", e);
        }
    }

    /**
     * 获取签名验证器
     * @return
    */

    @Bean("getClient")
    public CloseableHttpClient getClient(){
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        WechatPayHttpClientBuilder wechatPayHttpClientBuilder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId,mchSerialNo,privateKey)
                .withValidator(item->true);
        CloseableHttpClient build = wechatPayHttpClientBuilder.build();
        return build;


    }

}