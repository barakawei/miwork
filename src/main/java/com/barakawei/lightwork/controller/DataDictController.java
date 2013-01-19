package com.barakawei.lightwork.controller;

import com.barakawei.lightwork.domain.DataDict;
import com.barakawei.lightwork.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/dataDict")
public class DataDictController {

  @Autowired
  private DataDictService dataDictService;

  @ResponseBody
  @RequestMapping(value = "/{type}")
  public List<DataDict> name(@PathVariable("type") String type,String value) {
    return dataDictService.findDataDictByType(type,"%"+value+"%");
  }
}
