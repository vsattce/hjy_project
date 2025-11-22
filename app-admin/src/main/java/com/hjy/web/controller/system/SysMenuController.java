package com.hjy.web.controller.system;

import com.hjy.common.core.controller.TreeBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysMenu;
import com.hjy.system.service.impl.SysMenuServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "菜单管理", description = "系统菜单的增删改查操作")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends TreeBaseController<SysMenuServiceImpl, SysMenu,Long>
{

}