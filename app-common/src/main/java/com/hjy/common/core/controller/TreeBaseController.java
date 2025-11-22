package com.hjy.common.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.core.service.ITreeBaseService;

import lombok.Getter;

import java.util.List;

/**
 * 树形结构基础控制器类
 * 用于处理具有树形结构数据的公共操作
 *
 * @param <S> 继承了ITreeBaseService接口的服务类类型，用于处理树形结构数据
 * @param <T> 树形结构数据模型的类型
 * @author xucy
 * @date 2025-03-21 13:39:18
 */
//@Api(description = "树形结构基础控制器类")
//@RequestMapping("/common/core/treeBase")//需要注解路径，否则会导致界面报错
public class TreeBaseController<S extends ITreeBaseService<T, ID>, T, ID> extends BaseController<IBaseService<T>, T> {

    @Getter
    @Autowired(required = false)
    private S treeService;


    @GetMapping("/list4treeByRoot/{rootId}")
    public AjaxResult tree(@PathVariable ID rootId) {
//        ID id = (ID)rootId;
//        logger.debug("查询列表，参数: {}", parameters);
        System.out.println("rootId = " + rootId);
        List<T> list = treeService.list4treeByRoot(rootId);
//        List<T> list = getBaseService()
        return success(list);
    }
}
