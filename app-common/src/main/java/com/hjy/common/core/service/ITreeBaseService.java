package com.hjy.common.core.service;

import com.hjy.common.core.domain.TreeBaseEntity;

import java.util.List;

/**
 * ITreeService接口扩展了IService接口，为树形结构数据提供服务
 * 它继承了IService中的方法，并可能添加了与树结构相关的操作
 *
 * @param <T> 泛型参数，表示树节点的类型
 *
 * @author xucy
 * @date 2023/09/07
 */
public interface ITreeBaseService<T>  extends IBaseService<T> {
    /**
     * 查询通用通用对象，转化成树形结构
     * @param id id 不能为空
     * @return 树形结构
     *
     */
    TreeBaseEntity list4treeById(Integer id);

    /**
     * 查询通用通用对象，转化成树形结构
     * param parentId 父节点id 默认0
     * @return 树形结构
     *
     */
    List<TreeBaseEntity> list4treeByParentId(Integer parentId);
}
