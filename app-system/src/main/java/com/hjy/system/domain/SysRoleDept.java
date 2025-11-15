package com.hjy.system.domain;

import com.hjy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和部门关联 sys_role_dept
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleDept extends BaseEntity
{
    /** 角色ID */
    private Long roleId;
    
    /** 部门ID */
    private Long deptId;

}
