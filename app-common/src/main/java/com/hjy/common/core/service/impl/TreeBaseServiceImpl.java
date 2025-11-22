
package com.hjy.common.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjy.common.core.domain.TreeBaseEntity;
import com.hjy.common.core.domain.TreeEntity;
import com.hjy.common.core.service.ITreeBaseService;
import com.hjy.common.utils.TreeUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构服务实现类
 * 提供了针对树形数据结构的通用操作实现
 *
 * @param <M> 数据库映射接口类型，继承自BaseMapper
 * @param <T> 实体类型
 *
 * @author xucy
 * @date 2023/09/07
 */
//BaseTreeService<M extends BaseMapper<T>, T extends TreeEntity<T, ID>, ID>  extends ServiceImpl<M, T>
public class TreeBaseServiceImpl<M extends BaseMapper<T>, T extends TreeEntity<T,ID>,ID> extends BaseServiceImpl<M, T> implements ITreeBaseService<T,ID> {

    @Override
    public List<T> list4treeByRoot(ID root) {
        List<T> list = this.list();

        // 2. 调用之前的工具类
        // 这里的精髓是：因为 T 实现了 TreeEntity，我们可以直接引用接口的方法
        return TreeUtils.buildTree(
                list,
                TreeEntity::getId,        // 统一调接口的 getId
                TreeEntity::getParentId,  // 统一调接口的 getParentId
                TreeEntity::setChildren,  // 统一调接口的 setChildren
                root
        );
    }
}