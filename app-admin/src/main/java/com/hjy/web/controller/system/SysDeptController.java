package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysDept;
import com.hjy.system.service.impl.SysDeptServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController<SysDeptServiceImpl, SysDept>
{

}
