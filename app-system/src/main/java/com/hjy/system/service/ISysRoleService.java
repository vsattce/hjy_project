package com.hjy.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.core.service.IBaseService;

public interface ISysRoleService extends IBaseService<SysRole> {
    
    /**
     * 根据角色ID获取角色信息（包含菜单ID列表）
     * @param id 角色ID
     * @return 角色信息
     */
    SysRole getRoleInfoById(Long id);
    
    /**
     * 新增角色及其关联的菜单
     * @param entity 角色信息
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    int insertRoleWithRelations(SysRole entity);
    
    /**
     * 修改角色及其关联的菜单
     * @param entity 角色信息
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateRoleWithRelations(SysRole entity);
    
    /**
     * 批量删除角色及其关联的菜单
     * @param ids 角色ID列表
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    boolean deleteRolesWithRelationsByIds(List<Long> ids);
}
