package com.hjy.web.controller.system;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.utils.StringUtils;
import com.hjy.system.service.ISysRoleService;
import com.hjy.system.service.impl.SysRoleServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "角色管理", description = "角色信息的增删改查操作")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController<SysRoleServiceImpl, SysRole>
{
    private static final int MAX_BATCH_SIZE = 10;
    
    @Autowired
    private ISysRoleService sysRoleService;
    
    /**
     * 根据ID获取角色信息（包含菜单ID列表）
     */
    @Override
    @RequestMapping(value={"/{id}"})
    public AjaxResult getById(@PathVariable(value = "id", required = false) Long id)
    {
        if (StringUtils.isNotNull(id)) {
            SysRole sysRole = sysRoleService.getRoleInfoById(id);
            return AjaxResult.success(sysRole);
        }
        return AjaxResult.success();
    }
    
    /**
     * 新增角色
     */
    @Override
    @PostMapping
    public AjaxResult save(@RequestBody SysRole entity) {
        logger.info("新增角色数据: {}", entity);
        int result = sysRoleService.insertRoleWithRelations(entity);
        return toAjax(result == 1);
    }
    
    /**
     * 修改角色
     */
    @Override
    @PutMapping
    public AjaxResult updateById(@RequestBody SysRole entity) {
        logger.info("修改角色数据: {}", entity);
        boolean result = sysRoleService.updateRoleWithRelations(entity);
        return toAjax(result);
    }
    
    /**
     * 批量删除角色
     */
    @Override
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        if (ids == null || ids.length == 0) {
            return error("请选择要删除的数据");
        }
        if (ids.length > MAX_BATCH_SIZE) {
            return error("批量删除数量不能超过" + MAX_BATCH_SIZE + "条");
        }
        logger.info("批量删除角色数据，ID数量: {}", ids.length);
        boolean result = sysRoleService.deleteRolesWithRelationsByIds(Arrays.asList(ids));
        return toAjax(result);
    }
}
