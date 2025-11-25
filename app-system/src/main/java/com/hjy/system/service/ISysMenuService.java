package com.hjy.system.service;

import com.hjy.common.core.domain.entity.SysMenu;
import com.hjy.common.core.domain.entity.vo.SysMenuVo;
import com.hjy.common.core.service.IBaseService;
import com.hjy.common.core.service.ITreeBaseService;
import com.hjy.system.domain.vo.RouterVo;

import java.util.List;

public interface ISysMenuService extends ITreeBaseService<SysMenu,Long> {

    public List<SysMenuVo> selectMenuTreeByUserId(Long userId);

    public List<RouterVo> buildMenus(List<SysMenuVo> menus);
}
