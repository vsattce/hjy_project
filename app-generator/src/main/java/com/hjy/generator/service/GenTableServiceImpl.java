package com.hjy.generator.service;

import com.hjy.generator.domain.GenTable;
import com.hjy.generator.mapper.GenTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenTableServiceImpl implements IGenTableService {

    @Autowired
    private GenTableMapper genTableMapper;

    @Override
    public List<GenTable> selectGenTableAll() {
        return genTableMapper.selectGenTableAll();
    }

    @Override
    public List<Map<String, Object>> testMap() {
        return genTableMapper.testMap();
    }
}
