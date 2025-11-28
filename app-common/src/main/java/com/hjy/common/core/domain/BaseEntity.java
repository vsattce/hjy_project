package com.hjy.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;

/**
 * Entity基类
 *
 * @author ruoyi
 */

//@EqualsAndHashCode(callSuper = false)
//@Data
public class BaseEntity implements Serializable
{
//    private static final long serialVersionUID = 1L;

    /** 搜索值 */
//    @JsonIgnore
//    private String searchValue;

    /** 创建者 */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新者 */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private Map<String, Object> params;
}
