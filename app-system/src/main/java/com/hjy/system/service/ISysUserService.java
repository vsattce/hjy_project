package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.IBaseService;

public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
