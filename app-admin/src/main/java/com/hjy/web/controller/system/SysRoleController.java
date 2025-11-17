package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysRole;
import com.hjy.system.service.impl.SysRoleServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "角色管理", description = "角色信息的增删改查操作")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController<SysRoleServiceImpl, SysRole>
{

}
