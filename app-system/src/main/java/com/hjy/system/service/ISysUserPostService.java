package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.IBaseService;
import com.hjy.system.domain.SysUserPost;

public interface ISysUserPostService extends IBaseService<SysUserPost> {
    public boolean insertUserPostByUser(SysUser sysUser);
}
