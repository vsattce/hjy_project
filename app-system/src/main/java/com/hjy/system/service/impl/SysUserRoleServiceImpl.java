package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.mapper.SysUserRoleMapper;
import com.hjy.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void insertUserRoleByUser(SysUser entity) {

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

        return ;
    }

    @Override
    public boolean deleteAuthUser(SysUserRole sysUserRole) {
        // 1. 【核心安检】必须确保 ID 都不为空，否则拒绝执行
        if (sysUserRole.getUserId() == null || sysUserRole.getRoleId() == null) {
            throw new IllegalArgumentException("删除失败：关键参数丢失，禁止执行！");
            // 或者 return false; 看你业务怎么定
        }
        // 2. 【核心安检】必须确保用户 ID 和角色 ID 都存在，否则拒绝执行
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<SysUserRole>();
        queryWrapper.eq(SysUserRole::getUserId, sysUserRole.getUserId())
                .eq(SysUserRole::getRoleId, sysUserRole.getRoleId());

        return this.remove(queryWrapper);
//        return false;
    }

    @Override
    public boolean deleteAuthUsers(Long roleId, Long[] userIds) {
        // 1. 【防坑安检】如果 ID 列表是空的，直接返回，别骚扰数据库
        if (roleId == null || userIds == null || userIds.length == 0) {
            return false;
        }
        // 2. 构造批量删除
        // 注意：Arrays.asList(userIds) 把数组转成 List 塞给 in 方法
        return this.remove(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, Arrays.asList(userIds)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertAuthUsers(Long roleId, Long[] userIds) {
        // 1. 【防坑安检】如果 ID 列表是空的，直接返回，别骚扰数据库
        if (roleId == null || userIds == null || userIds.length == 0) {
            return false;
        }

        return this.saveBatch(Arrays.stream(userIds).map(userId->new SysUserRole(userId, roleId)).toList());
    }

}
