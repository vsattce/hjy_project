package com.hjy.system.domain;

import com.hjy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleMenu extends BaseEntity
{
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;

}
