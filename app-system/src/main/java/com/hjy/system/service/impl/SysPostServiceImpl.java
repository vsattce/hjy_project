package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysPost;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysPostMapper;
import com.hjy.system.service.ISysPostService;
import com.hjy.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public List<SysPost> getByUserId(Long id) {

        List< SysUserRole> sysUserRoles = sysUserRoleService.list(new SysUserRole(id, null));
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("post_id", sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));

        return super.list(queryWrapper);
    }
}
