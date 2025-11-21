package com.hjy.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.*;

import com.hjy.common.core.domain.TreeBaseEntity;
import com.hjy.common.core.service.ITreeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.utils.DateUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

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
@RequestMapping("/common/core/treeBase")//需要注解路径，否则会导致界面报错
public class TreeBaseController<S extends ITreeBaseService<T>, T> extends BaseController<IBaseService<T>, T> {

    @Autowired
    private S baseService;

    //  @Autowired
/*     public TreeController(ITreeService<T> treeService) {
        super(treeService);
    } */

    @Override
    public S getBaseService() {
        return this.baseService;
    }

    /**
     * 根据id查询通用对象，转化成树形结构
     */
    @Operation(
            summary ="查询列表",
            description ="根据id查询通用对象，转化成树形结构"
            )
    @RequestMapping(value = "/list4treeById", method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult list4treeById(Integer id) {
        if (Objects.isNull(id)) {
            return error("id 不能为空");
        }
        TreeBaseEntity treeBaseEntity = baseService.list4treeById(id);
        return success(treeBaseEntity);
    }

    /**
     * 根据父id查询通用对象，转化成树形结构
     */
    @Operation(
            summary ="查询列表",
            description ="根据父id查询通用对象，转化成树形结构"
    )
    @RequestMapping(value = "/list4treeByParentId", method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult list4treeByParentId(Integer parentId) {
        if (Objects.isNull(parentId)) {
            parentId = 0;
        }
        List<TreeBaseEntity> treeEntities = baseService.list4treeByParentId(parentId);
        return success(treeEntities);
    }
}
