
package com.hjy.common.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjy.common.core.domain.TreeBaseEntity;
import com.hjy.common.core.service.ITreeBaseService;
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
public class TreeBaseServiceImpl<M extends BaseMapper<T>, T> extends BaseServiceImpl<M, T> implements ITreeBaseService<T> {
    @Override
    public TreeBaseEntity list4treeById(Integer id) {
        Assert.notNull(id, "id 不能为空");
        T parentT = super.getById(id);
        if (ObjUtil.isEmpty(parentT)) {
            return new TreeBaseEntity();
        }
        List<TreeBaseEntity> childrentreeBaseEntityList =list4treeByParentId(id);
        TreeBaseEntity treeBaseEntity = BeanUtil.toBean(parentT, TreeBaseEntity.class);
        //增加自己
        return treeBaseEntity;
//        return new TreeBaseEntity(treeBaseEntity.getId(), treeBaseEntity.getParentId(), treeBaseEntity.getCode(), treeBaseEntity.getName(), treeBaseEntity.getEnable(), childrentreeBaseEntityList) ;
    }

    @Override
    public List<TreeBaseEntity> list4treeByParentId(Integer parentId) {
        if (ObjUtil.isEmpty(parentId)) {
            parentId = 0;
        }
        //数据量不多的情况下，直接查询所有，后续可以加缓存，提高效率
        List<T> allFlatList = super.list();
        if(ObjUtil.isEmpty(allFlatList)){
            return new ArrayList<TreeBaseEntity>();
        }

        List<TreeBaseEntity> flatTreeBaseEntityList = BeanUtil.copyToList(allFlatList, TreeBaseEntity.class);
        List<TreeBaseEntity> treeBaseEntityList = null; //TreeBaseEntity.buildTree(flatTreeBaseEntityList, parentId);

        return treeBaseEntityList;
    }
}