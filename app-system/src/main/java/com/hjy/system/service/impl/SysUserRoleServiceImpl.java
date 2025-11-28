package com.hjy.system.service.impl;

import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysUserRoleMapper;
import com.hjy.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public boolean insertUserRoleByUser(SysUser entity) {

        List<SysUserRole> sysUserRoles = new ArrayList<>();
        if (entity.getRoleIds() != null) {
            for (Long roleId : entity.getRoleIds()) {
                sysUserRoles.add(new SysUserRole(entity.getUserId(), roleId));
            }
            if (!sysUserRoles.isEmpty()) {
                sysUserRoleMapper.insert(sysUserRoles);
//                this.saveBatch(sysUserRoles);
            }
        }

        return true;
    }

}
