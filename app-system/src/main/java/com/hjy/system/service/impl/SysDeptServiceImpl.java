package com.hjy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hjy.common.core.domain.entity.SysDept;
import com.hjy.common.core.service.impl.BaseServiceImpl;
import com.hjy.common.core.service.impl.TreeBaseServiceImpl;
import com.hjy.system.mapper.SysDeptMapper;
import com.hjy.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysDeptServiceImpl extends TreeBaseServiceImpl<SysDeptMapper, SysDept,Long> implements ISysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeTreeByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }

        Set<Long> idSet = new HashSet<>(Arrays.asList(ids));

        for (Long id : ids) {
            LambdaQueryWrapper<SysDept> lqw = new LambdaQueryWrapper<SysDept>();
            lqw.apply("(find_in_set({0}, ancestors))", id);
            List<SysDept> sysDepts = this.list(lqw);
            sysDepts.forEach(sysDept -> {
                idSet.add(sysDept.getDeptId());
            });
            System.out.println("ids = " + this.list(lqw));
//            this.remove(lqw);
        }
        // 3. 一次性批量删除（性能高，且避开了 Error 1093）
        if (!idSet.isEmpty()) {
            return this.removeBatchByIds(idSet);
        }

        return true;
    }
}
