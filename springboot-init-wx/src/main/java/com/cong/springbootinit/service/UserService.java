package com.cong.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cong.springbootinit.model.dto.user.TokenUserRequest;
import com.cong.springbootinit.model.dto.user.WxLoginRequest;
import com.cong.springbootinit.model.entity.User;
import com.cong.springbootinit.model.vo.UserDetailVo;
import com.cong.springbootinit.model.vo.UserVo;


/**
* @author 86188
* @description 针对表【user(微信用户信息表)】的数据库操作Service
* @createDate 2022-09-17 20:36:58
*/
public interface UserService extends IService<User> {
    /**
     * 微信用户登录
     * @param wxLoginRequest
     * @return
     */
    UserVo login(WxLoginRequest wxLoginRequest);

    /**
     * 根据id获取用户的详细信息
     * @param id
     * @return
     */
    UserDetailVo getUserDetailById(Long id);

    /**
     * 更新用户信息
     * @param userDetailVo
     * @param tokenUserRequest
     * @return
     */
    Boolean updateUser(UserDetailVo userDetailVo, TokenUserRequest tokenUserRequest);
}
