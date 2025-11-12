package com.hjy.generator.controller;

import com.hjy.common.core.controller.BaseController;
import com.hjy.generator.domain.GenTable;
import com.hjy.generator.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    @GetMapping("/list")
    public List<GenTable> getList(){
//        List<GenTable> gts = new ArrayList<GenTable>();
//        gts.add(new GenTable());
        return genTableService.selectGenTableAll();
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
