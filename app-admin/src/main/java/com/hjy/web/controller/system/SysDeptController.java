package com.hjy.web.controller.system;

import com.hjy.common.core.controller.TreeBaseController;
import com.hjy.common.core.domain.AjaxResult;
import com.hjy.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjy.common.core.controller.BaseController;
import com.hjy.common.core.domain.entity.SysDept;
import com.hjy.system.service.impl.SysDeptServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Arrays;

@Tag(name = "部门管理", description = "部门信息的增删改查操作")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends TreeBaseController<SysDeptServiceImpl, SysDept,Long>
{
    @Autowired
    private ISysDeptService sysDeptService;

    @Override
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        boolean result = sysDeptService.removeTreeByIds(ids);
        return toAjax(result);
    }
}
