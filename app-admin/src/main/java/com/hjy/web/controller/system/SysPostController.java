package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysPost;
import com.hjy.system.service.impl.SysPostServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController<SysPostServiceImpl, SysPost>
{

}
