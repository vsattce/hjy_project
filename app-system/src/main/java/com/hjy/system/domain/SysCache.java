package com.hjy.system.domain;

import com.hjy.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 缓存信息
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysCache extends BaseEntity
{
    /** 缓存名称 */
    private String cacheName = "";

    /** 缓存键名 */
    private String cacheKey = "";

    /** 缓存内容 */
    private String cacheValue = "";

    /** 备注 */
    private String remark = "";

}
