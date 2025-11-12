package com.hjy.generator.service;

import com.hjy.generator.domain.GenTable;

import java.util.List;
import java.util.Map;

public interface IGenTableService {

    public List<GenTable> selectGenTableAll();

    public List<Map<String,Object>> testMap();
}
