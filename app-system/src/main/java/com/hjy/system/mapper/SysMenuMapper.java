package com.hjy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjy.common.core.domain.entity.SysMenu;
import com.hjy.common.core.domain.entity.vo.SysMenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    public List<SysMenuVo> selectMenuTreeByUserId(Long userId);

    public List<SysMenuVo> selectMenuTreeAll();

}
