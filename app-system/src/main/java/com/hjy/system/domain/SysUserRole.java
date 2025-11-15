package com.hjy.system.domain;

import com.hjy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联 sys_user_role
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends BaseEntity
{
    /** 用户ID */
    private Long userId;
    
    /** 角色ID */
    private Long roleId;

}
