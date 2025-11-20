package com.hjy.system.service.impl;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.mapper.SysUserMapper;
import com.hjy.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }
}
