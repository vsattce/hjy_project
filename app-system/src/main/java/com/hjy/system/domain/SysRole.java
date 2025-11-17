package com.hjy.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hjy.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色对象 sys_role
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseEntity
{
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long id;
    /** 角色ID */
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色权限字符串 */
    private String roleKey;

    /** 显示顺序 */
    private Integer roleSort;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限） */
    private String dataScope;

    /** 菜单树选择项是否关联显示 */
    private boolean menuCheckStrictly;

    /** 部门树选择项是否关联显示 */
    private boolean deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
