package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysDept;
import com.hjy.system.service.impl.SysDeptServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "部门管理", description = "部门信息的增删改查操作")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController<SysDeptServiceImpl, SysDept>
{

}
