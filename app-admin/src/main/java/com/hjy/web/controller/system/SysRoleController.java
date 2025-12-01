package com.hjy.web.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.service.ISysUserRoleService;
import com.hjy.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;
    
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

//

    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public AjaxResult allocatedList(Page<SysUser> page, SysUser sysUser, @RequestParam(value = "roleId", required = true)Long roleId)
    {
//        Long roleId = (Long)map.get("roleId");
        IPage<SysUser> list = sysUserService.pageByRoleId(page,roleId,sysUser);
        return success(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public AjaxResult unallocatedList(Page<SysUser> page, SysUser sysUser, @RequestParam(value = "roleId", required = true)Long roleId)
    {
        IPage<SysUser> list = sysUserService.pageUnallocatedByRoleId(page,roleId,sysUser);
        return success(list);
    }

    /**
     * 取消授权用户
     */
    @PutMapping("/authUser/cancel")
    public AjaxResult cancelAuthUser(@RequestBody SysUserRole userRole)
    {
        return toAjax(sysUserRoleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(Long roleId, Long[] userIds)
    {
        return toAjax(sysUserRoleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(Long roleId, Long[] userIds)
    {
//        sysUserRoleService.checkRoleDataScope(roleId);
        return toAjax(sysUserRoleService.insertAuthUsers(roleId, userIds));
    }
}
