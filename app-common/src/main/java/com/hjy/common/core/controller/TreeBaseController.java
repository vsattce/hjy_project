package com.hjy.common.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.core.service.ITreeBaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;

/**
 * 树形结构基础控制器类
 * 用于处理具有树形结构数据的公共操作
 *
 * @param <S> 继承了ITreeBaseService接口的服务类类型，用于处理树形结构数据
 * @param <T> 树形结构数据模型的类型
 * @param <ID> 主键ID类型
 * @author xucy
 * @date 2025-03-21 13:39:18
 */
//@Tag(name = "树形结构基础接口", description = "提供树形结构数据的查询操作")
public class TreeBaseController<S extends ITreeBaseService<T, ID>, T, ID> extends BaseController<IBaseService<T>, T> {

    @Getter
    @Autowired(required = false)
    private S treeService;

    /**
     * 根据根节点ID查询树形结构列表
     *
     * @param rootId 根节点ID
     * @return 树形结构数据列表
     */
    @Operation(
            summary = "查询树形结构",
            description = "根据根节点ID查询完整的树形结构数据，包含所有子节点"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = AjaxResult.class))),
            @ApiResponse(responseCode = "404", description = "根节点不存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @Parameter(name = "rootId", description = "根节点ID", required = true, in = ParameterIn.PATH)
    @GetMapping("/list4treeByRoot/{rootId}")
    public AjaxResult list4treeByRoot(@PathVariable ID rootId) {
        logger.debug("查询树形结构，根节点ID: {}", rootId);
        List<T> list = treeService.list4treeByRoot(rootId);
        return success(list);
    }
}
