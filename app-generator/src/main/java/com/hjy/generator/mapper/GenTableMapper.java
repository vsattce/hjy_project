package com.hjy.generator.mapper;

import com.hjy.generator.domain.GenTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface GenTableMapper {
    public List<GenTable> selectGenTableAll();
}
