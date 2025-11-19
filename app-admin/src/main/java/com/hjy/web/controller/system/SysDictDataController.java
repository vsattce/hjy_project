package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysDictData;
import com.hjy.system.service.impl.SysDictDataServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "字典数据管理", description = "字典数据的增删改查操作")
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController<SysDictDataServiceImpl, SysDictData>
{

}
