package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysUser;
import com.hjy.system.service.impl.SysUserServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "用户管理", description = "用户信息的增删改查操作")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUserServiceImpl, SysUser>
{

}
