package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysPost;
import com.hjy.system.service.impl.SysPostServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "岗位管理", description = "岗位信息的增删改查操作")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController<SysPostServiceImpl, SysPost>
{

}
