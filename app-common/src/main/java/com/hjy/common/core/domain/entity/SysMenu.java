package com.hjy.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hjy.common.core.domain.BaseEntity;

import com.hjy.common.core.domain.TreeBaseEntity;
import com.hjy.common.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限表 sys_menu
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends TreeBaseEntity<SysMenu,Long> implements TreeEntity<SysMenu,Long>
{
    /** 菜单ID */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;
    // 1. 重新声明 id 字段
    // 2. 加上 exist = false，告诉 MP 忽略它
//    @TableField(exist = false)
//    private Long id;
    /** 菜单名称 */
    private String menuName;

//    /** 父菜单ID */
//    private Long parentId;
////
////    @TableField(exist = false)
//    private List<SysMenu> children;
    /** 显示顺序 */
    private Integer orderNum;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 路由参数 */
    private String query;

    /** 是否为外链（0是 1否） */
    private Integer isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    private Integer isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    private String visible;

    /** 菜单状态（0正常 1停用） */
    private String status;

    /** 权限标识 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    @Override
    public Long getId() {
        return this.menuId;
    }


//    @Override
//    public void setChildren(List<SysMenu> children) {
//        return ;
//    }

//    @Override
//    public void setChildren(List<SysMenu> children) {
//
//    }
}
