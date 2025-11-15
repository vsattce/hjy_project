package com.hjy.system.domain;

import com.hjy.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和岗位关联 sys_user_post
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserPost extends BaseEntity
{
    /** 用户ID */
    private Long userId;
    
    /** 岗位ID */
    private Long postId;

}
