package com.cong.springbootinit.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author lhc
 * @date 2022-09-19 20:57
 */
public class JwtUtils {
    public static final String SALT = "!X%$#ERS15@AXS";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String getToken(Map<String,Object> map){
        Calendar instance = Calendar.getInstance();
        //默认七天过期
        instance.add(Calendar.DATE,7);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            if (v instanceof Long){
                builder.withClaim(k, ((Long) v).longValue());

            }else {
                builder.withClaim(k, v.toString());
            }
        });
            //指定令牌过期时间
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SALT));

        return token;
    }

    /**
     * 验证token 合法性
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
    }

    /**
     * 获取token的方法
     * @param token
     * @return
     */
    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
        return verify;

    }

}
