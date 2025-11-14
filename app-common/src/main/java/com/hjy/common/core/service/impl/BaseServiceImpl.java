
package com.hjy.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.utils.QueryWrapperUtils;

import java.util.List;
import java.util.Map;

/**
 * 基础服务实现类，提供通用的 CRUD 操作
 * 该类扩展了 ServiceImpl 类，并实现了 IBaseService 接口
 * 使用泛型设计，使其能适用于不同的数据访问层接口和实体类
 *
 * @param <M> 继承自 BaseMapper 的数据访问层接口类型
 * @param <T> 实体类类型
 *
 * @author xucy
 * @date 2023/09/07
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {
    @Override
    public List<T> list(T entity, Map<String,Object> parameters) {
        QueryWrapper<T> queryWrapper = QueryWrapperUtils.entity2Wrapper(entity,parameters);
        return  super.list(queryWrapper);
    }

    @Override
    public List<T> list(T entity) {
        return  this.list(entity,null);
    }
    @Override
    public IPage<T> page(IPage<T> iPage, T entity, Map<String,Object> parameters) {
        QueryWrapper<T> queryWrapper =QueryWrapperUtils.entity2Wrapper(entity,parameters);
        return super.page(iPage, queryWrapper);
    }

    @Override
    public IPage<T> page(IPage<T> iPage, T entity) {
        return this.page(iPage, entity,null);
    }
}