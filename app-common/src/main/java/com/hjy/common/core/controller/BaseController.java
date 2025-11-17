package com.hjy.common.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用控制器基类
 * 提供标准的CRUD操作接口
 *
 * @param <S> Service类型，必须继承IBaseService
 * @param <T> 实体类型
 */
public class BaseController<S extends IBaseService<T>, T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 批量操作最大数量限制
     */
    private static final int MAX_BATCH_SIZE = 1000;

    @Getter
    @Autowired(required = false)
    private S baseService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 查询通用对象列表（不分页）
     *
     * @param entity 查询条件实体
     * @param parameters 额外查询参数
     * @return 查询结果列表
     */
    @ApiOperation( "查询通用对象")
    @GetMapping("/list")
    public AjaxResult list(T entity, @RequestParam Map<String, Object> parameters) {
        logger.debug("查询列表，参数: {}", parameters);
        List<T> list = baseService.list(entity, parameters);
        return success(list);
    }

    /**
     * 分页查询通用对象
     *
     * @param page 分页参数
     * @param entity 查询条件实体
     * @param parameters 额外查询参数
     * @return 分页查询结果
     */
    @ApiOperation( "查询分页对象")
    @GetMapping("/page")
    public AjaxResult page(Page<T> page, T entity, @RequestParam Map<String, Object> parameters) {
        logger.debug("分页查询，页码: {}, 每页数量: {}", page.getCurrent(), page.getSize());
        IPage<T> entityIPage = baseService.page(page, entity, parameters);
        return success(entityIPage);
    }

    /**
     * 根据ID获取通用对象详细信息
     *
     * @param id 对象ID
     * @return 对象详情
     */
    @ApiOperation( "根据ID获取通用对象详细信息")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {
        logger.debug("查询详情，ID: {}", id);
        try {
            T entity = baseService.getById(id);
            if (entity == null) {
                return error("数据不存在");
            }
            return success(entity);
        } catch (Exception e) {
            logger.error("查询数据异常，ID: {}", id, e);
            return error("数据不存在");
        }
    }

    /**
     * 新增通用对象
     *
     * @param entity 待新增的实体对象
     * @return 操作结果
     */
    @ApiOperation( "新增通用对象")
    @PostMapping
    public AjaxResult save(@RequestBody T entity) {
        logger.info("新增数据: {}", entity);
        boolean result = baseService.save(entity);
        return toAjax(result);
    }

    /**
     * 修改通用对象
     *
     * @param entity 待修改的实体对象
     * @return 操作结果
     */
    @ApiOperation( "修改通用对象")
    @PutMapping
    public AjaxResult updateById(@RequestBody T entity) {
        logger.info("修改数据: {}", entity);
        boolean result = baseService.updateById(entity);
        return toAjax(result);
    }

    /**
     * 批量删除通用对象
     *
     * @param ids 待删除的ID数组
     * @return 操作结果
     */
    @ApiOperation( "批量删除通用对象")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        if (ids == null || ids.length == 0) {
            return error("请选择要删除的数据");
        }
        if (ids.length > MAX_BATCH_SIZE) {
            return error("批量删除数量不能超过" + MAX_BATCH_SIZE + "条");
        }
        logger.info("批量删除数据，ID数量: {}", ids.length);
        boolean result = baseService.removeByIds(Arrays.asList(ids));
        return toAjax(result);
    }

    /**
     * 新增或修改通用对象
     *
     * @param entity 待保存的实体对象
     * @return 操作结果
     */
    @ApiOperation( "新增或修改通用对象")
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody T entity) {
        logger.info("保存或更新数据: {}", entity);
        boolean result = baseService.saveOrUpdate(entity);
        return toAjax(result);
    }

    // ==================== 响应结果封装方法 ====================

    /**
     * 返回成功
     */
    protected AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    protected AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    protected AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回成功消息
     */
    protected AjaxResult success(Object data) {
        return AjaxResult.success(data);
    }

    /**
     * 返回失败消息
     */
    protected AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回警告消息
     */
    protected AjaxResult warn(String message) {
        return AjaxResult.warn(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error("操作失败");
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error("操作失败");
    }


}
