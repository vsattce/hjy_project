package com.hjy.system.service;

import com.hjy.common.core.service.IBaseService;
import com.hjy.system.domain.SysPost;

import java.util.List;

public interface ISysPostService extends IBaseService<SysPost> {

    List<SysPost> getByUserId(Long id);
}
