package com.hjy.web.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.system.domain.SysNotice;
import com.hjy.system.service.impl.SysNoticeServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "通知公告管理", description = "系统通知公告的增删改查操作")
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController<SysNoticeServiceImpl, SysNotice>
{

}
