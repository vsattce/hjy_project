package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.IBaseService;
import com.hjy.system.domain.SysUserRole;

public interface ISysUserRoleService extends IBaseService<SysUserRole> {

    public void insertUserRoleByUser(SysUser sysUser);

    public boolean deleteAuthUser(SysUserRole sysUserRole);

    public boolean deleteAuthUsers(Long roleId, Long[] userIds);

    public boolean insertAuthUsers(Long roleId, Long[] userIds);
}
