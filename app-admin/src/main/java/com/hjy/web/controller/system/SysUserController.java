package com.hjy.web.controller.system;

import com.hjy.common.core.domain.AjaxResult;
import com.hjy.common.core.domain.entity.SysRole;
import com.hjy.common.utils.StringUtils;
import com.hjy.system.service.ISysPostService;
import com.hjy.system.service.ISysRoleService;
import com.hjy.system.service.ISysUserRoleService;
import com.hjy.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.system.service.impl.SysUserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

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

//    为了参数传空的时候不报错误
    @RequestMapping(value={"/info/","/info/{id}"})
    public AjaxResult getUserInfoById(@PathVariable(value = "id",required = false) Long id)
    {
        AjaxResult ajaxResult = AjaxResult.success();
        if (StringUtils.isNotNull(id)){
            SysUser sysUser = sysUserService.getUserInfoById(id);
            ajaxResult.put(AjaxResult.DATA_TAG, sysUser);
            ajaxResult.put("postIds", sysPostService.getByUserId(id));
            ajaxResult.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        List<SysRole> roles = sysRoleService.list();
        ajaxResult.put("roles", SysUser.isAdmin( id)?roles:roles.stream().filter(r->!r.isAdmin()).collect(Collectors.toList()));
        ajaxResult.put("posts", sysPostService.list());

        return ajaxResult;
    }
}
