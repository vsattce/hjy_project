package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysDictData;
import com.hjy.system.service.impl.SysDictDataServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController<SysDictDataServiceImpl, SysDictData>
{

}
