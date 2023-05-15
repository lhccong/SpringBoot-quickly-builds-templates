package com.cong.springbootinit.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import com.cong.springbootinit.common.ErrorCode;
import com.cong.springbootinit.exception.BusinessException;
import com.cong.springbootinit.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lhc
 * @date 2022-09-19 21:40
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=================经过了token拦截=================");
        //获取请求头中的令牌
        String token = request.getHeader("token");
        if (token==null){
            throw new BusinessException(ErrorCode.NO_AUTH,"无token");
        }

        try {
            //请求放行
            JwtUtils.verify(token);

            return true;
        }catch (SignatureVerificationException e){
            //签名不一致异常
            throw new BusinessException(ErrorCode.NO_AUTH,"签名不一致异常");
        }catch (TokenExpiredException e){
            //token过期异常
            throw new BusinessException(ErrorCode.NO_AUTH,"token过期异常");
        }catch (AlgorithmMismatchException e){
            //算法不匹配异常
            throw new BusinessException(ErrorCode.NO_AUTH,"算法不匹配异常");
        }catch (InvalidClaimException e){
            //失效的payload异常
            throw new BusinessException(ErrorCode.NO_AUTH,"失效的payload异常");
        }
    }
}
