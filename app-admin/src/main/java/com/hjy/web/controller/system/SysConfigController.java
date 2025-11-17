package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysConfig;
import com.hjy.system.service.impl.SysConfigServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "系统配置管理", description = "系统参数配置的增删改查操作")
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController<SysConfigServiceImpl, SysConfig>
{

}
