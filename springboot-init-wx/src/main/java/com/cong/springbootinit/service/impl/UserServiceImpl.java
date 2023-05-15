package com.cong.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.cong.springbootinit.common.ErrorCode;
import com.cong.springbootinit.exception.BusinessException;
import com.cong.springbootinit.mapper.UserMapper;
import com.cong.springbootinit.model.dto.user.TokenUserRequest;
import com.cong.springbootinit.model.dto.user.WxLoginRequest;
import com.cong.springbootinit.model.entity.User;
import com.cong.springbootinit.model.entity.UserDetail;
import com.cong.springbootinit.model.vo.UserDetailVo;
import com.cong.springbootinit.model.vo.UserVo;
import com.cong.springbootinit.service.UserDetailService;
import com.cong.springbootinit.service.UserService;
import com.cong.springbootinit.utils.GetWxMessage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 86188
* @description 针对表【user(微信用户信息表)】的数据库操作Service实现
* @createDate 2022-09-17 20:36:58
*/
@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private GetWxMessage getWxMessage;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserDetailService userDetailService;
    @Override
    public UserVo login(WxLoginRequest wxLoginRequest) {
        if (wxLoginRequest==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取微信用户的最新数据
        User user = getWxMessage.getSessionKeyOrOpenid(wxLoginRequest);
        //原数据库的用户数据
        User dbUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, user.getOpenId()));
        if (dbUser==null){
            this.save(user);
        }else {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setNickName(user.getNickName());
            dbUser.setGender(user.getGender());
            this.updateById(dbUser);
        }
        UserVo userVo = new UserVo();
        //对用户数据进行筛选
        BeanUtils.copyProperties(user,userVo);
        //如果数据库存在这个用户则对userVo赋予id
        if (dbUser!=null){
            userVo.setId(dbUser.getId());
        }
        return userVo;

    }

    @Override
    public UserDetailVo getUserDetailById(Long id) {

        MPJLambdaWrapper<UserDetailVo> wrapper = new MPJLambdaWrapper<UserDetailVo>()
                .selectAll(User.class)
                .selectAll(UserDetail.class)
                .leftJoin(UserDetail.class,UserDetail::getUserId,User::getId)
                .eq(User::getId,id);
        UserDetailVo userDetailVo = userMapper.selectJoinOne(UserDetailVo.class, wrapper);
        return userDetailVo;
    }

    @Override
    public Boolean updateUser(UserDetailVo userDetailVo, TokenUserRequest tokenUserRequest) {
        String email = userDetailVo.getEmail();
        //邮箱编号不为空 判断邮箱编号的格式
        if (StringUtils.isNotBlank(email)){
            //判断邮箱正则表达式
            String validPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
            Matcher matcher = Pattern.compile(validPattern).matcher(email);
            if (!matcher.find()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"邮箱格式错误");
            }
        }
        String phone = userDetailVo.getPhone();
        //手机号码不为空  判断手机号码格式
        if (StringUtils.isNotBlank(phone)){
            //判断手机正则表达式
            String validPattern = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
            Matcher matcher = Pattern.compile(validPattern).matcher(phone);
            if (!matcher.find()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"手机号格式错误");
            }
        }

        String realName = userDetailVo.getRealName();
        //真实姓名不为空  判断真实姓名格式
        if (StringUtils.isNotBlank(realName)){
            //判断手机正则表达式
            String validPattern = "^[\\u4e00-\\u9fa5]{0,}$";
            Matcher matcher = Pattern.compile(validPattern).matcher(realName);
            if (!matcher.find()){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"真实姓名格式错误");
            }
        }
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(userDetailVo,userDetail);
        LambdaQueryWrapper<UserDetail> wrapper = new LambdaQueryWrapper<>();
        //根据用户id更新值
        wrapper.eq(UserDetail::getUserId,tokenUserRequest.getId());
        //更新
        boolean save = userDetailService.update(userDetail,wrapper);
        if (!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"更新失败");
        }
        return true;
    }

}




