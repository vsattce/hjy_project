package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysMenu;
import com.hjy.system.service.impl.SysMenuServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController<SysMenuServiceImpl, SysMenu>
{

}