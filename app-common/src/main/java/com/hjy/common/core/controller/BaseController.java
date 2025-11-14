package com.hjy.common.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjy.common.constant.HttpStatus;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.page.TableDataInfo;
import com.hjy.common.core.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BaseController<S extends IBaseService<T>,T> {

    @Autowired(required = false)
    private  S baseService;

    public S getBaseService() {
        return baseService;
    }
    /**
     * 查询通用对象不分页
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult list(T entity , @RequestParam Map<String,Object> parameters) {
        List<T> list = baseService.list(entity,parameters);
        return success(list);
    }

    /**
     * 查询通用对象
     */
    @RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult page(Page<T> page, T entity, @RequestParam Map<String,Object> parameters) {
        IPage<T> entityIPage = baseService.page(page, entity,parameters);
        return success(entityIPage);
    }

    /**
     * 获取通用对象详细信息
     */
    @RequestMapping(value = "/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult getById(@PathVariable Long id){
        return success( baseService.getById(id));
    }

    /**
     * 新增通用对象
     */
    @PostMapping
    public AjaxResult save(@RequestBody T entity){
        return success(baseService.save(entity));
    }

    /**
     * 修改通用对象
     */
    @PutMapping
    public AjaxResult updateById(@RequestBody T entity){
        return success(baseService.updateById(entity));
    }

    /**
     * 批量通用对象
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids){
        return success(baseService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 新增或修改通用对象
     */
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(@RequestBody T entity){
        return success(baseService.saveOrUpdate(entity));
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(Object data)
    {
        return AjaxResult.success(data);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回警告消息
     */
    public AjaxResult warn(String message)
    {
        return AjaxResult.warn(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }


}
