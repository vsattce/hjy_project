package com.hjy.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hjy.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表 sys_post
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPost extends BaseEntity
{
    @TableId(value = "post_id", type = IdType.AUTO)
    private Long id;
    /** 岗位序号 */
    private Long postId;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    private String postName;

    /** 岗位排序 */
    private Integer postSort;

    /** 状态（0正常 1停用） */
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;
}
