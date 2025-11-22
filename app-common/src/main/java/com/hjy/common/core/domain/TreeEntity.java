package com.hjy.common.core.domain;

import java.util.List;

// T: 实体类本身, ID: 主键类型(Long/String)
public interface TreeEntity<T, ID> {


    ID getId();                 // 获取主键
    ID getParentId();           // 获取父ID
    void setChildren(List<T> children); // 设置子节点
}