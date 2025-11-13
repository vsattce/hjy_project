package com.hjy.common.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * IBaseService接口继承自IService，用于定义针对特定实体类型T的基本服务方法
 * 这个接口的存在是为了提供一个更广泛的服务定义，可能包含一些基础的、通用的操作
 * 它扩展了IService的功能，允许在不同的业务场景下对实体进行更广泛的处理
 *
 * @param <T> 表示实体类型的参数，使得接口可以用于不同的实体类
 *
 * @author xucy
 * @date 2023/09/07
 */
public interface IBaseService<T>  extends IService<T> {
    /**
     * 根据通用对象属性和parameters查询查询通用对象列表
     * 此方法使用了MyBatis-Plus的QueryWrapper来构建查询条件，通过将通用对象和Map对象对象转换为查询封装器，
     * 可以灵活地执行条件查询
     *
     * @param entity 对象，包含查询条件的属性值
     * @param parameters Map对象，包含非等号的查询条件
     * @return 匹配查询条件的通用对象列表
     */
    List<T> list(T entity, Map<String,Object> parameters);

    /**
     * 根据通用对象属性查询查询通用对象列表
     * 此方法使用了MyBatis-Plus的QueryWrapper来构建查询条件，通过将通用对象对象转换为查询封装器，
     * 可以灵活地执行条件查询
     *
     * @param entity 对象，包含查询条件的属性值
     * @return 匹配查询条件的通用对象列表
     */
    List<T> list(T entity);

    /**
     * 根据通用对象属性和parameters查询查询通用对象列表
     *
     * @param iPage 分页对象，包含分页参数如当前页码、每页大小等
     * @param entity 对象，包含查询条件的属性值，用于构建查询Wrapper
     * @param parameters Map对象，包含非等号的查询条件
     * @return 返回封装了查询结果的分页对象，包含总记录数、总页数及通用对象列表
     */
    IPage<T> page(IPage<T> iPage, T entity, Map<String,Object> parameters);



    /**
     * 根据通用对象属性查询查询通用对象列表
     *
     * @param iPage 分页对象，包含分页参数如当前页码、每页大小等
     * @param entity 对象，包含查询条件的属性值，用于构建查询Wrapper
     * @return 返回封装了查询结果的分页对象，包含总记录数、总页数及通用对象列表
     */
    IPage<T> page(IPage<T> iPage, T entity);
}