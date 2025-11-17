package com.hjy.web.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysConfig;
import com.hjy.system.service.ISysConfigService;
import com.hjy.system.service.impl.SysConfigServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(description = "系统设置接口")
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController<SysConfigServiceImpl, SysConfig>
{

}
