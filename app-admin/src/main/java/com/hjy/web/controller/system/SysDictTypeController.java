package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysDictType;
import com.hjy.system.service.impl.SysDictTypeServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "字典类型管理", description = "字典类型的增删改查操作")
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController<SysDictTypeServiceImpl, SysDictType>
{

}
