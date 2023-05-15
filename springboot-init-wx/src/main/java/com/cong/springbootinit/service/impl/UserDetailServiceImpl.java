package com.cong.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cong.springbootinit.mapper.UserDetailMapper;
import com.cong.springbootinit.model.entity.UserDetail;
import com.cong.springbootinit.service.UserDetailService;
import org.springframework.stereotype.Service;

/**
* @author 86188
* @description 针对表【user_detail(用户详细信息表)】的数据库操作Service实现
* @createDate 2022-09-20 14:30:47
*/
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail>
    implements UserDetailService {

}




