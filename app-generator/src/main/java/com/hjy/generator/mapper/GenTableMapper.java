package com.hjy.generator.mapper;

import com.hjy.generator.domain.GenTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface GenTableMapper {
    public List<GenTable> selectGenTableAll();

    public List<Map<String,Object>> testMap();
}
