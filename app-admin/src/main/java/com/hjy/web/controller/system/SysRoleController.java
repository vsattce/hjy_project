package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysRole;
import com.hjy.system.service.impl.SysRoleServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController<SysRoleServiceImpl, SysRole>
{

}
