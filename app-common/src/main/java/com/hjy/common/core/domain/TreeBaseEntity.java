package com.hjy.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TreeVo树结构实体类
 *
 * @author xucy
 * @Date 2025-03-21 13:39:18
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Alias("treeBaseEntity")//别名，防止mybatis和ruoyi的名字冲突
public class TreeBaseEntity<T,ID> implements Serializable {
   // private static final long serialVersionUID = 1L;
    /**
     * 节点ID
     */
//    private Integer id;
    /**
     * 父ID
     */
    private ID parentId;
//    getParentId
    /**
     * 节点代码
     */
//    private String code;

    /**
     * 节点名称
     */
//    private String name;

    /**
     * 是否启用(0不启用,1启用)
     */
//    private String enable;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<T> children;


    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
//    private String remark;
}
