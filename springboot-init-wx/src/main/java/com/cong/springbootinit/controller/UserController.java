package com.cong.springbootinit.controller;


import com.cong.springbootinit.common.BaseResponse;
import com.cong.springbootinit.common.ErrorCode;
import com.cong.springbootinit.common.ResultUtils;
import com.cong.springbootinit.exception.BusinessException;
import com.cong.springbootinit.model.dto.user.TokenUserRequest;
import com.cong.springbootinit.model.dto.user.WxLoginRequest;
import com.cong.springbootinit.model.vo.UserDetailVo;
import com.cong.springbootinit.model.vo.UserVo;
import com.cong.springbootinit.service.UserService;
import com.cong.springbootinit.utils.JsonToMap;
import com.cong.springbootinit.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户
 * @author lhc
 * @date 2022-09-17 21:30
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 微信登录
     * @param wxLoginRequest
     * @return
     */

    @PostMapping("/login")
    public BaseResponse login(@RequestBody(required = false) WxLoginRequest wxLoginRequest){
        if (wxLoginRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVo user = userService.login(wxLoginRequest);
        String token = JwtUtils.getToken(JsonToMap.objectToMap(user));
        user.setKbToken(token);
        return ResultUtils.success(user);
    }




    /**
     * 根据id查看用户详细信息
     * @return
     */
    @GetMapping("/messageById")
    public BaseResponse messageById(TokenUserRequest tokenUserRequest){
        if (tokenUserRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"传入id错误");
        }
        UserDetailVo user = userService.getUserDetailById(tokenUserRequest.getId());

        return ResultUtils.success(user);
    }
    /**
     * 更新用户信息
     * @return
     */
    @PostMapping("/updateUser")
    public BaseResponse updateUser(@RequestBody UserDetailVo userDetailVo, TokenUserRequest tokenUserRequest){
        if (userDetailVo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (tokenUserRequest==null){
            throw new BusinessException(ErrorCode.NO_AUTH);

        }
        userService.updateUser(userDetailVo,tokenUserRequest);

        return ResultUtils.success(true);
    }
    /**
     * 获取当前用户接口
     */
    @PostMapping("/current")
    @ApiOperation(value = "获取当前登录用户信息")
    public BaseResponse getCurrentLoginUser(TokenUserRequest tokenUserRequest){
        return ResultUtils.success(tokenUserRequest);
    }
}
