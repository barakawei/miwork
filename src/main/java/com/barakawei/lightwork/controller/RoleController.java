package com.barakawei.lightwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import java.util.List;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.service.RoleService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.HashSet;
import com.barakawei.lightwork.domain.Organization;
import com.barakawei.lightwork.domain.Privilege;
import com.barakawei.lightwork.domain.User;
import java.util.Date;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @ResponseBody
  @RequestMapping(value = "/listData", method = RequestMethod.GET)
  public List<Role> listData() {
    int page = 0;
    int pageSize = 50;
    Pageable pageable = new PageRequest(page, pageSize);
    List<Role> roles = roleService.findRoles(pageable).getContent();
    return roles;
  }
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public String delete(@PathVariable("id") String id) {
    roleService.deleteRoleById(id);
    return "redirect:/role/list";

  }
  @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(){
      //for(int i =10000;i < 100000;i++){
        //Role r = new Role();
        //r.setName("role"+i);
        //r.setCreateDate(new Date());
        //r.setEnable(false);
        //roleService.saveRole(r);
      //}
      return "role/list_role";
    }
}
