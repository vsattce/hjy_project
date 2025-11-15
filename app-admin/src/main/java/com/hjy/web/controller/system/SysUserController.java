package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysUser;
import com.hjy.system.service.impl.SysUserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController<SysUserServiceImpl, SysUser>
{

}
