package com.hjy.web.controller.system;

import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.utils.SecurityUtils;
import com.hjy.common.utils.StringUtils;
import com.hjy.system.domain.SysPost;
import com.hjy.system.domain.SysUserPost;
import com.hjy.system.domain.SysUserRole;
import com.hjy.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.system.service.impl.SysUserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "用户管理", description = "用户信息的增删改查操作")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUserServiceImpl, SysUser>
{
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

    @PostMapping
    public AjaxResult save(@RequestBody SysUser entity) {
        logger.info("新增数据: {}", entity);
//
        entity.setPassword(SecurityUtils.encryptPassword(entity.getPassword()));
        int result = sysUserService.insertUserWithRelations(entity);

        return toAjax(result==1);
    }
}
