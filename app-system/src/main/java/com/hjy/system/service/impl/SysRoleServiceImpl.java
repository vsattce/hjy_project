package com.hjy.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.system.domain.SysRoleMenu;
import com.hjy.system.mapper.SysRoleMapper;
import com.hjy.system.service.ISysRoleMenuService;
import com.hjy.system.service.ISysRoleService;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;
    
    @Override
    public SysRole getRoleInfoById(Long id) {
        SysRole sysRole = this.getById(id);
        if (sysRole != null) {
            // 查询角色关联的菜单ID列表
            List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(
                new QueryWrapper<SysRoleMenu>().eq("role_id", id)
            );
            if (sysRoleMenus != null && !sysRoleMenus.isEmpty()) {
                List<Long> menuIds = sysRoleMenus.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toList());
                sysRole.setMenuIds(menuIds);
            }
        }
        return sysRole;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRoleWithRelations(SysRole entity) {
        // 1. 保存角色基本信息
        boolean saveRoleResult = this.save(entity);
        if (!saveRoleResult) {
            throw new RuntimeException("角色保存失败，直接抛异常回滚");
        }
        
        // 2. 保存角色菜单关联
        insertRoleMenuByRole(entity);
        
        return 1;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleWithRelations(SysRole entity) {
        // 1. 更新角色基本信息
        boolean updateRoleResult = this.updateById(entity);
        if (!updateRoleResult) {
            throw new RuntimeException("角色更新失败，直接抛异常回滚");
        }
        
        // 2. 删除旧的角色菜单关联
        sysRoleMenuService.remove(
            new QueryWrapper<SysRoleMenu>().eq("role_id", entity.getRoleId())
        );
        
        // 3. 保存新的角色菜单关联
        insertRoleMenuByRole(entity);
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRolesWithRelationsByIds(List<Long> ids) {
        // 1. 删除角色基本信息
        boolean deleteRoleResult = this.removeByIds(ids);
        if (!deleteRoleResult) {
            throw new RuntimeException("角色删除失败，直接抛异常回滚");
        }
        
        // 2. 删除角色菜单关联
        sysRoleMenuService.remove(
            new QueryWrapper<SysRoleMenu>().in("role_id", ids)
        );
        
        return true;
    }
    
    /**
     * 插入角色菜单关联
     * @param role 角色信息
     */
    private void insertRoleMenuByRole(SysRole role) {
        if (role.getMenuIds() != null && !role.getMenuIds().isEmpty()) {
            List<SysRoleMenu> roleMenuList = role.getMenuIds().stream()
                .map(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(role.getRoleId());
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                })
                .collect(Collectors.toList());
            sysRoleMenuService.saveBatch(roleMenuList);
        }
    }
}
