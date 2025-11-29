package com.hjy.web.controller.system;

import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.utils.SecurityUtils;
import com.hjy.common.utils.StringUtils;
import com.hjy.system.domain.SysPost;
import com.hjy.system.domain.SysUserPost;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.system.service.impl.SysUserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "用户管理", description = "用户信息的增删改查操作")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUserServiceImpl, SysUser>
{
    private static final int MAX_BATCH_SIZE = 10;
    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysPostService sysPostService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserPostService sysUserPostService;

//    为了参数传空的时候不报错误
    @Override
    @RequestMapping(value={"/","/{id}"})
    public AjaxResult getById(@PathVariable(value = "id",required = false) Long id)
    {
        AjaxResult ajaxResult = AjaxResult.success();
        if (StringUtils.isNotNull(id)){
            SysUser sysUser = sysUserService.getUserInfoById(id);
            ajaxResult.put(AjaxResult.DATA_TAG, sysUser);
            ajaxResult.put("postIds", sysPostService.getByUserId(id).stream().map(SysPost::getPostId).collect(Collectors.toList()));
            ajaxResult.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        List<SysRole> roles = sysRoleService.list();
        ajaxResult.put("roles", SysUser.isAdmin( id)?roles:roles.stream().filter(r->!r.isAdmin()).collect(Collectors.toList()));
        ajaxResult.put("posts", sysPostService.list());

        return ajaxResult;
    }

    @Override
    @PostMapping
    public AjaxResult save(@RequestBody SysUser entity) {
        logger.info("新增数据: {}", entity);
//
        entity.setPassword(SecurityUtils.encryptPassword(entity.getPassword()));
        int result = sysUserService.insertUserWithRelations(entity);

        return toAjax(result==1);
    }

    @Override
    @PutMapping
    public AjaxResult updateById(@RequestBody SysUser entity) {
        logger.info("修改数据: {}", entity);
        boolean result = sysUserService.updateUserWithRelations(entity);
        return toAjax(result);
    }

    /**
     * 批量删除通用对象
     *
     * @param ids 待删除的ID数组
     * @return 操作结果
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        if (ids == null || ids.length == 0) {
            return error("请选择要删除的数据");
        }
        if (ids.length > MAX_BATCH_SIZE) {
            return error("批量删除数量不能超过" + MAX_BATCH_SIZE + "条");
        }
        logger.info("批量删除数据，ID数量: {}", ids.length);
        boolean result = sysUserService.deleteUsersWithRelationsByIds(Arrays.asList(ids));
        return toAjax(result);
    }
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(sysUserService.resetPwd(user));
    }
//
    /**
     * 根据用户编号获取授权角色
     */
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long id)
    {
        AjaxResult ajaxResult = AjaxResult.success();
//        if (StringUtils.isNotNull(userId)){
        SysUser sysUser = sysUserService.getUserInfoById(id);
        ajaxResult.put("user", sysUser);
        ajaxResult.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));

        return ajaxResult;
    }

    /**
     * 用户授权角色
     */
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        sysUserService.insertUserAuth(userId, roleIds);
        return success();
    }
}
