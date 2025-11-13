package com.hjy.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class QueryWrapperUtils {
//    /**
//     * entity和parameters转查询条件
//     *
//     * @param entity
//     * @param parameters
//     * @return QueryWrapper<E>
//     */
//    public static <E> QueryWrapper<E> entity2Wrapper(E entity, Map<String, Object> parameters) {
//        return entity2Wrapper(new QueryWrapper<>(), entity, parameters);
//    }
//
    /**
     * entity和parameters转查询条件
     *
     * @param wrapper
     * @param entity
     * @param parameters
     * @return QueryWrapper<E>
     */
//    public static <E> QueryWrapper<E> entity2Wrapper(QueryWrapper<E> wrapper, E entity, Map<String, Object> parameters) {
//        if (Objects.isNull(wrapper)) {
//            wrapper = new QueryWrapper<>();
//        }
//
//        if (Objects.isNull(wrapper.getEntityClass())) {
//            wrapper.setEntityClass((Class<E>) entity.getClass());
//        }
//        Field[] fields = ReflectUtil.getFields(wrapper.getEntityClass());
//        if (ArrayUtil.isEmpty(fields)) {
//            return wrapper;
//        }
//
//        //设置实体类会导致多一个表达式
//        if (Objects.isNull(wrapper.getEntity())) {
//            wrapper.setEntity(entity);
//        }
//        //叠加外界的Map
//        if (CollUtil.isNotEmpty(parameters)) {
//            map2Wrapper(wrapper, parameters);
//        }
//        return wrapper;
//    }
}
