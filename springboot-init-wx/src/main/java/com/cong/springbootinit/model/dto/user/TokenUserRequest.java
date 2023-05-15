package com.cong.springbootinit.model.dto.user;

import com.cong.springbootinit.common.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 过滤器所返回的数据信息
 * @author lhc
 * @date 2022-09-20 11:51
 */
@Data
@Api(hidden = true)
public class TokenUserRequest extends PageRequest {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "id", required = true,hidden = true)

    private Long id;
    /**
     * 表示微信用户的名称
     */
    @ApiModelProperty(value = "nickName", required = true,hidden = true)
    private String nickName;

    /**
     * 微信用户的头像地址
     */
    @ApiModelProperty(value = "avatarUrl", required = true,hidden = true)
    private String avatarUrl;
}
