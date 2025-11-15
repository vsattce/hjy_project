package com.hjy.web.controller.system;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysNotice;
import com.hjy.system.service.impl.SysNoticeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController<SysNoticeServiceImpl, SysNotice>
{

}
