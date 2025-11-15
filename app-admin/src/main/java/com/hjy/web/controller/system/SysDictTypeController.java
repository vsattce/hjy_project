package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysDictType;
import com.hjy.system.service.impl.SysDictTypeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController<SysDictTypeServiceImpl, SysDictType>
{

}
