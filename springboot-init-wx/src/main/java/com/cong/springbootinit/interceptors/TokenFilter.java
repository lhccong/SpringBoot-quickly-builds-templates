package com.cong.springbootinit.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.cong.springbootinit.common.BaseResponse;
import com.cong.springbootinit.common.ErrorCode;
import com.cong.springbootinit.common.ResultUtils;
import com.cong.springbootinit.utils.JwtUtils;
import com.cong.springbootinit.wrapper.BodyRequestWrapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author lhc
 * @date 2022-09-20 09:47
 */
public class TokenFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/login", "/v2", "/favicon.ico", "/swagger-ui.html","/rank/shop",
                    "/doc.html", "/webjars", "/swagger-resources", "/v2/api-docs","/chat")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("============初始化过滤器================");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        int i = -1;
        for (String allowedPath : ALLOWED_PATHS) {
             i = path.indexOf(allowedPath);
             if (i!=-1){
                 break;
             }
        }
        if (i!=-1){
            filterChain.doFilter(servletRequest, servletResponse);

        }
        else {

            System.out.println("===进入验证===");
            //获取请求头中的令牌
            BodyRequestWrapper requestWrapper = new BodyRequestWrapper((HttpServletRequest) servletRequest);
            String token = requestWrapper.getHeader("token");
            if (StringUtils.isBlank(token)) {
                handler(servletResponse,"无token");
//                throw new BusinessException(ErrorCode.NO_AUTH, "无token");
            }
            try {
                //请求放行
                DecodedJWT decodedJWT = JwtUtils.getTokenInfo(token);
                Map<String, Claim> claims = decodedJWT.getClaims();
                Iterator<String> it = claims.keySet().iterator();
                //迭代器循环赋值
                while (it.hasNext()) {
                    String key = it.next();
                    //如果key为id则给他转成long类型
                    if (key.equals("id")){
                        String subData = claims.get(key).asString();
                        int index = subData.lastIndexOf(".");
                        requestWrapper.addParameter(key, Long.valueOf(subData.substring(0, index)));
                    }else {
                        requestWrapper.addParameter(key, claims.get(key).asString());
                    }
                }
                //放行
                filterChain.doFilter(requestWrapper, servletResponse);

            } catch (SignatureVerificationException e) {
                //签名不一致异常
                handler(servletResponse,"签名不一致异常");
            } catch (TokenExpiredException e) {
                //token过期异常
                handler(servletResponse,"token过期异常");
            } catch (AlgorithmMismatchException e) {
                //算法不匹配异常
                handler(servletResponse,"算法不匹配异常");
            } catch (InvalidClaimException e) {
                //失效的payload异常
                handler(servletResponse,"失效的payload异常");
            }
        }


    }

    @Override
    public void destroy() {
        System.out.println("============过滤器销毁================");

        Filter.super.destroy();
    }

    private void handler(ServletResponse response,String Message){
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try{
            PrintWriter writer = response.getWriter();
            BaseResponse error = ResultUtils.error(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMessage());
            Gson gson = new Gson();
            String s = gson.toJson(error);
            writer.print(s);
            writer.flush();
            writer.close();
        }catch (Exception e){

        }



    }

}
