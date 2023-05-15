package com.cong.springbootinit.utils;


import com.cong.springbootinit.common.ErrorCode;
import com.cong.springbootinit.exception.BusinessException;
import com.cong.springbootinit.model.dto.user.WxLoginRequest;
import com.cong.springbootinit.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lhc
 * @date 2022-05-23 10:27
 */
@Component
@ConfigurationProperties(prefix = "wx.config.login")
@Data
@Log4j2
public class GetWxMessage {

    @Resource
    private RestTemplate restTemplate;

    private String grant_type;

    private String secret;

    private String appid;

    private String requestUrl;


    public User getSessionKeyOrOpenid(WxLoginRequest wxLoginRequest) {
        //微信端登录code值
        String wxCode = wxLoginRequest.getCode();
        Map<String, String> requestUrlParam = new HashMap<>();
        //开发者设置中的appId
        requestUrlParam.put("appid", appid);
        //开发者设置中的appSecret
        requestUrlParam.put("secret", secret);
        //小程序调用wx.login返回的code
        requestUrlParam.put("js_code", wxCode);
        //默认参数 authorization_code
        requestUrlParam.put("grant_type", grant_type);
        String param = "";
        Iterator<String> it = requestUrlParam.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + requestUrlParam.get(key) + "&";
        }
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity(requestUrl + param, String.class);
        JsonObject jsonObject = new Gson().fromJson(forEntity.getBody(), JsonObject.class);
        if (jsonObject.get("errmsg")!=null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"调用官方接口错误");
        }
        String session_key = jsonObject.get("session_key").getAsString();
        String openid = jsonObject.get("openid").getAsString();
        JsonObject message = null;
        try {
            message = AesCbcUtil.decrypt(wxLoginRequest.getData(), session_key, wxLoginRequest.getIv());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "解密失败");
        }
        message.addProperty("openId", openid);

        User user = new Gson().fromJson(message, User.class);
        return user;

    }
}

